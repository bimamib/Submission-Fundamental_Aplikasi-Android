package com.bima.githubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bima.githubuser.databinding.ActivityFavoriteUserBinding
import com.bima.githubuser.ui.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Favorite Users"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserFavorite.layoutManager = layoutManager

        favoriteUserViewModel = obtainFavoriteUserViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllFavoriteUsers().observe(this) {
            val adapter = FavoriteUserAdapter()
            adapter.submitList(it)
            binding.rvUserFavorite.adapter = adapter
        }
    }

    private fun obtainFavoriteUserViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(
            this@FavoriteUserActivity,
            factory
        )[FavoriteUserViewModel::class.java]
    }
}