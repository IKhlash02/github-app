package com.dicoding.github.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.data.remote.response.ItemsItem
import com.dicoding.github.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val _followingData= MutableLiveData<List<ItemsItem>?>()
    val followingData: MutableLiveData<List<ItemsItem>?> = _followingData

    private val _followersData= MutableLiveData<List<ItemsItem>?>()
    val followersData: MutableLiveData<List<ItemsItem>?> = _followersData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private  const val TAG = "FollowingViewModel"
    }


 fun findFollowing(index: Int, username:String) {
        _isLoading.value = true
        val client = if(index == 1){
            ApiConfig.getApiService().getFollowers(username)
        } else{
            ApiConfig.getApiService().getFollowing(username)
        }

        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false

                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        if(index == 1){
                            _followersData.value = responseBody
                        }
                        else {
                            _followingData.value = responseBody
                        }

                    }
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}