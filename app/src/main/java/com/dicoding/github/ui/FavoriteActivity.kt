package com.dicoding.github.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.databinding.ActivityFavoriteBinding
import com.dicoding.github.ui.adapter.FavoriteAdapter
import com.dicoding.github.ui.viewmodels.FavoriteViewFactory
import com.dicoding.github.ui.viewmodels.FavoriteViewModel


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewFactory.getInstance(application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

      favoriteViewModel.getAllFavorites().observe(this){
          if(it != null){
              adapter.setListFavorites(it)
          }
      }

        adapter = FavoriteAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter

    }
}