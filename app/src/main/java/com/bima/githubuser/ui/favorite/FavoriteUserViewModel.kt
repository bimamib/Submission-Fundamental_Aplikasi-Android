package com.bima.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.githubuser.data.local.entity.FavoriteUser
import com.bima.githubuser.data.repository.UserRepository
import kotlinx.coroutines.launch

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    /*private val _listUserFavorite = LiveData<List<FavoriteUser>>()
    val listUserFavorite: LiveData<List<FavoriteUser>> = _listUserFavorite*/

    fun getAllFavoriteUsers() = userRepository.getAllFavoriteUsers()

    /*private fun getAllFavoriteUsers() {
        viewModelScope.launch {
            _listUserFavorite.postValue(userRepository.getAllFavoriteUsers())
        }
    }*/

    init {
        getAllFavoriteUsers()
    }
}