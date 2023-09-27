package com.bima.githubuser.data.remote.retrofit

import com.bima.githubuser.data.response.DetailResponse
import com.bima.githubuser.data.response.GitHubResponse
import com.bima.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}
