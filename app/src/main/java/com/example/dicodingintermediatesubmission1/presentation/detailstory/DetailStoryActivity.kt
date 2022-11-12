package com.example.dicodingintermediatesubmission1.presentation.detailstory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import coil.load
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.databinding.ActivityDetailStoryBinding
import com.example.dicodingintermediatesubmission1.presentation.homepage.HomePageFragment

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bindData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun bindData() {
        val storyData = intent.getParcelableExtra<Story>(HomePageFragment.STORY_DATA)
        supportActionBar?.title = storyData?.name
        binding.apply {
            ivStoryImage.load(storyData?.photoUrl)
            tvStoryTitle.text = storyData?.name
            tvDescription.text = storyData?.description
        }
    }
}