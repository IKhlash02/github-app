package com.dicoding.github.data.remote.retrofit
import com.dicoding.github.data.remote.response.ItemsItem
import com.dicoding.github.data.remote.response.ResponSearchUser
import com.dicoding.github.data.remote.response.ResponseUser
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("search/users")
    fun getSearchResult(
        @Query("q") query: String
    ): Call<ResponSearchUser>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseUser>
}