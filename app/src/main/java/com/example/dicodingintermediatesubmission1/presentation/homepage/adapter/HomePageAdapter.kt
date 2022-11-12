package com.example.dicodingintermediatesubmission1.presentation.homepage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.databinding.StoryItemBinding

class HomePageAdapter(private val onClick: StoryOnClickListener) :
    PagingDataAdapter<Story, HomePageAdapter.HomePageViewHolder>(differCallback) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }


    class HomePageViewHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Story?) {
            with(item) {
                binding.ivStory.load(this?.photoUrl)
                binding.tvTitle.text = this?.name ?: ""
                binding.tvCreatedAt.text = this?.createdAt ?: ""
                binding.tvDescription.text = this?.description ?: ""
            }
        }
    }

    interface StoryOnClickListener {
        fun onClick(item: Story)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bindView(currentItem)
        holder.itemView.setOnClickListener {
            currentItem?.let {
                onClick.onClick(it)
            }
        }
    }

}