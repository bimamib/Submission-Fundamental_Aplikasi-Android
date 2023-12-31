package com.bima.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bima.githubuser.databinding.FragmentFollowBinding
import com.bima.githubuser.ui.FollowUserAdapter
import com.bima.githubuser.ui.ViewModelFactory

class FollowFragment : Fragment() {

    private var position = 0
    var username: String = ""

    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_USERNAME = "0"
        const val ARG_POSITION = "Bima Prasetio"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = obtainDetailViewModel(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "bimamib"
        }

        detailViewModel.getFollowing(username)
        detailViewModel.getFollower(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        detailViewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            detailViewModel.userFollower.observe(viewLifecycleOwner) {
                val adapter = FollowUserAdapter()
                adapter.submitList(it)
                binding.rvFollow.adapter = adapter
            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) {
                val adapter = FollowUserAdapter()
                adapter.submitList(it)
                binding.rvFollow.adapter = adapter
            }
        }
    }

    private fun obtainDetailViewModel(activity: FragmentActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(requireActivity(), factory)[DetailViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarFollow.visibility = View.VISIBLE
            } else {
                progressBarFollow.visibility = View.GONE
            }
        }
    }
}