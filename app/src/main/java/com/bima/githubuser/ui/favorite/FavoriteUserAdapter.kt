package com.bima.githubuser.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bima.githubuser.data.local.entity.FavoriteUser
import com.bima.githubuser.databinding.ListUserBinding
import com.bima.githubuser.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class FavoriteUserAdapter :
    ListAdapter<FavoriteUser, FavoriteUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item_name: FavoriteUser) {
            binding.tvItemName.text = item_name.username
            Glide.with(binding.root)
                .load(item_name.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra("ID", item_name.name)
                intentDetail.putExtra("USERNAME", item_name.username)
                intentDetail.putExtra("AVATAR", item_name.avatarUrl)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}