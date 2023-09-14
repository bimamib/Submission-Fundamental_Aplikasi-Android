package com.bima.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bima.githubuser.data.response.GitHubResponse
import com.bima.githubuser.data.response.ItemsItem
import com.bima.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel (){

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _listUser

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loadingScreen

    companion object {
        private const val TAG = "UserViewModel"
//        private const val DEFAULT = "ikram"
    }

    init {
        findGitHub("bima")
    }

    private fun findGitHub(query: String) {
        _loadingScreen.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object: Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                _loadingScreen.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                    Log.d("SAT","onCreate: ${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _loadingScreen.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}