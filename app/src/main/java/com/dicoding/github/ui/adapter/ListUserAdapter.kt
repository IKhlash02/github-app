package com.dicoding.github.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.github.data.remote.response.ItemsItem
import com.dicoding.github.databinding.ItemNameBinding

class ListUserAdapter(private val listUser:List<ItemsItem>): RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

    class ViewHolder(var binding: ItemNameBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.binding.ivUser)
        holder.binding.tvUsername.text = user.login

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(user.login)
        }

    }
}