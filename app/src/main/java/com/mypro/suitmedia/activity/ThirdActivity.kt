package com.mypro.suitmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mypro.suitmedia.R
import com.mypro.suitmedia.adapter.UserAdapter
import com.mypro.suitmedia.databinding.ActivityThirdBinding
import com.mypro.suitmedia.model.UserResponse
import com.mypro.suitmedia.util.EXTRA_USER_NAME
import com.mypro.suitmedia.util.toast
import com.mypro.suitmedia.viewmodel.ThirdActivityViewModel

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private val viewModel: ThirdActivityViewModel by viewModels()
    private val adapter: UserAdapter by lazy { UserAdapter(this) }
    private var page = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_third)

        initView()
        getData()
    }

    private fun initView() {
        with(binding) {
            with(toolbar) {
                tvTitle.text = getString(R.string.third_screen)
                btnBack.setOnClickListener {
                    finish()
                }
            }

            val layoutManager = LinearLayoutManager(this@ThirdActivity)
            rvUser.layoutManager = layoutManager
            rvUser.adapter = adapter
            adapter.onClick = { firstName, lastName->
                val intent = Intent()
                intent.putExtra(EXTRA_USER_NAME, "$firstName $lastName")
                setResult(RESULT_OK, intent)
                finish()
                //
            }

            rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val itemCount = layoutManager.itemCount
                    val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = itemCount.minus(1) == lastVisiblePosition
                    if (isLastPosition && page < totalPages) {
                        viewModel.getUsers(++page, 10)
                    }
                }
            })

            refresh.setOnRefreshListener {
                page = 1
                getData()
            }
        }
    }

    private fun getData() {
        viewModel.getUsers(page, 10)
        viewModel.userResponse.observe(this, ::observeUser)
        viewModel.errorResponse?.observe(this, ::observeError)
        binding.isLoading = true
    }

    private fun observeUser(response: UserResponse) {
        with(response) {
            if (page == 1)
                adapter.setDataSet(data)
            else
                adapter.addDataSet(data)

            this@ThirdActivity.totalPages = totalPages
            binding.isLoading = false
            binding.refresh.isRefreshing = false

            binding.isEmpty = data.isEmpty()
        }
    }

    private fun observeError(err: String) {
        toast(err)
    }
}