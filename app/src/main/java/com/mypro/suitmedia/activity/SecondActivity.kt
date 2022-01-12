package com.mypro.suitmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mypro.suitmedia.R
import com.mypro.suitmedia.databinding.ActivitySecondBinding
import com.mypro.suitmedia.util.EXTRA_NAME
import com.mypro.suitmedia.util.EXTRA_USER_NAME

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        //Get intent extra
        name = intent.getStringExtra(EXTRA_NAME).orEmpty()

        initView()
    }

    private fun initView() {
        with(binding) {
            with(toolbar) {
                tvTitle.text = getString(R.string.second_screen)
                btnBack.setOnClickListener {
                    finish()
                }
            }

            tvName.text = name

            btnChoose.setOnClickListener {
                resultLauncher.launch(Intent(this@SecondActivity, ThirdActivity::class.java))
            }
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val name = data?.getStringExtra(EXTRA_USER_NAME)
                binding.tvUsername.text = name
            }
        }

}