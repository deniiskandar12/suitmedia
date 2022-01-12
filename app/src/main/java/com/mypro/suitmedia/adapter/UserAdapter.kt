package com.mypro.suitmedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mypro.suitmedia.databinding.RowUserBinding
import com.mypro.suitmedia.model.User
import com.mypro.suitmedia.util.loadImage

class UserAdapter(
    val context: Context
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var dataSet = mutableListOf<User>()
    var onClick: ((String, String) -> Unit)? = null

    inner class ViewHolder(binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgPhoto = binding.imgPhoto
        val tvName = binding.tvName
        val tvEmail = binding.tvEmail

        init {
            itemView.setOnClickListener {
                onClick?.invoke(dataSet[adapterPosition].firstName, dataSet[adapterPosition].lastName)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.ViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val user = dataSet[position]
            with(user) {
                imgPhoto.loadImage(context, avatar)
                tvName.text = "$firstName $lastName"
                tvEmail.text = email
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setDataSet(list: MutableList<User>) {
        this.dataSet.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addDataSet(list: MutableList<User>) {
        this.dataSet.apply {
            addAll(list)
        }
        notifyItemRangeInserted(itemCount, list.size)
    }

}