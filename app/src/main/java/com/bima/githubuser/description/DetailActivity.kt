package com.bima.githubuser.description

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bima.githubuser.R
import com.bima.githubuser.databinding.ActivityDetailBinding
import com.bima.githubuser.ui.SectionPagerAdapter
import com.bima.githubuser.ui.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("USERNAME")
        val avatar = intent.getStringExtra("AVATAR")

        detailViewModel = obtainDetailViewModel(this@DetailActivity)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (username != null) {
            val sectionPagerAdapter = SectionPagerAdapter(this, username)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.ivProfile)
                binding.tvName.text = it.name
                binding.tvUsername.text = it.login
                binding.tvFollower.text = "${it.followers} Follower"
                binding.tvFollowing.text = "${it.following} Following"
            }
        }

        detailViewModel.loading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressDetailBar.visibility = View.VISIBLE
            } else {
                progressDetailBar.visibility = View.GONE
            }
        }
    }

    private fun obtainDetailViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this@DetailActivity, factory).get(DetailViewModel::class.java)
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tv_follower,
            R.string.tv_following
        )
    }
}
