package com.bima.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bima.githubuser.data.repository.UserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    fun getAllFavoriteUsers() = userRepository.getAllFavoriteUsers()

    init {
        getAllFavoriteUsers()
    }
}