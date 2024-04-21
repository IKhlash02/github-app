package com.dicoding.github.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.github.data.local.entity.Favorite
import com.dicoding.github.databinding.ItemNameBinding
import com.dicoding.github.helper.FavoriteDiffCallback
import com.dicoding.github.ui.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorites = ArrayList<Favorite>()

    fun setListFavorites(listFavorites: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
       val favorite = listFavorites[position]
        holder.binding.tvUsername.text = favorite.username
        Glide.with(holder.itemView.context)
            .load(favorite.avatarUrl)
            .into(holder.binding.ivUser)

        holder.itemView.setOnClickListener{
           val intentDetail =  Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_NAME, favorite.username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }
    override fun getItemCount(): Int {
        return listFavorites.size
    }
    class FavoriteViewHolder(val binding: ItemNameBinding): RecyclerView.ViewHolder(binding.root)
}