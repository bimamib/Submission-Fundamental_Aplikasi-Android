package com.bima.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bima.githubuser.R
import com.bima.githubuser.data.response.ItemsItem
import com.bima.githubuser.databinding.ActivityMainBinding
import com.bima.githubuser.ui.favorite.FavoriteUserActivity
import com.bima.githubuser.ui.setting.SettingActivity

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
            searchBar.inflateMenu(R.menu.option_menu)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.show()
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

            searchBar.setOnMenuItemClickListener { menuitem ->
                when (menuitem.itemId) {
                    R.id.menu1 -> {
                        val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu2 -> {
                        val intent = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    else -> false
                }
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