package com.dicoding.github.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.data.remote.response.ItemsItem
import com.dicoding.github.data.remote.response.ResponSearchUser
import com.dicoding.github.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _followingData = MutableLiveData<List<ItemsItem>>()
    val followingData: LiveData<List<ItemsItem>> = _followingData

    private val _isLoading = MutableLiveData<Boolean>()
     val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private  const val TAG = "MainViewModel"
        private const val DEFAULT_SEARCH = "a"
    }

    init {
        findUserGithub(DEFAULT_SEARCH)
    }

    fun findUserGithub(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchResult(username)
        client.enqueue(object : Callback<ResponSearchUser> {
            override fun onResponse(
                call: Call<ResponSearchUser>,
                response: Response<ResponSearchUser>,
            ) {
                _isLoading.value = false

                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                       _followingData.value =  responseBody.items
                    }
                } else{

                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponSearchUser>, t: Throwable) {
               _isLoading.value = false

                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}