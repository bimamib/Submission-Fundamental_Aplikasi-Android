package com.bima.githubuser.data.retrofit

import com.bima.githubuser.data.response.GitHubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<GitHubResponse>
}
