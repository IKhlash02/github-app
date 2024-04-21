package com.dicoding.github.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.data.local.entity.Favorite
import com.dicoding.github.data.repository.FavoriteRepository
import com.dicoding.github.data.remote.response.ResponseUser
import com.dicoding.github.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<Favorite> = mFavoriteRepository.getFavoriteUserByUsername(username)


    private val _detailUser= MutableLiveData<ResponseUser?>()
    val detailUser: MutableLiveData<ResponseUser?> = _detailUser



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
        private  const val TAG = "DetailViewModel"
    }


     fun showDetailUser(userName: String) {
       _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(userName)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {

                _isLoading.value =  false

                if(response.isSuccessful){
                    val responseDetail = response.body()

                    if(responseDetail != null){
                        _detailUser.value = responseDetail
                    }
                } else{

                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value = false

                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}