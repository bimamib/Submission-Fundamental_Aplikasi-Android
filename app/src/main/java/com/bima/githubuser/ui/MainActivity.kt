package com.bima.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bima.githubuser.data.response.ItemsItem
import com.bima.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    userViewModel.findGitHub(searchView.text.toString())
                    userViewModel.user.observe(this@MainActivity) {
                        if (it.isNullOrEmpty()) {
                            showNotFound(true)
                        } else {
                            showNotFound(false)
                        }
                    }
                    false
                }
        }

        userViewModel.loading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        userViewModel.user.observe(this) {
            if (it != null) {
                setUserData(it)
            }
        }
    }

    private fun setUserData(datauser: List<ItemsItem>) {
        val adapter = UserAdapter(datauser)
        adapter.submitList(datauser)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarUser.visibility = View.VISIBLE
            } else {
                progressBarUser.visibility = View.GONE
            }
        }
    }

    private fun showNotFound(isDataNotFound: Boolean) {
        binding.apply {
            if (isDataNotFound) {
                rvUser.visibility = View.GONE
                errorMessage.visibility = View.VISIBLE
            } else {
                rvUser.visibility = View.VISIBLE
                errorMessage.visibility = View.GONE
            }
        }
    }
}