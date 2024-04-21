package com.dicoding.github.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.data.local.entity.Favorite
import com.dicoding.github.data.repository.FavoriteRepository

class FavoriteViewModel(aplication: Application) : ViewModel(){
    private val mFavoriteRepository = FavoriteRepository(aplication)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()
}