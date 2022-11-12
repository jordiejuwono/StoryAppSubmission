package com.example.dicodingintermediatesubmission1.presentation.homepage

import android.app.Activity.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingintermediatesubmission1.base.arch.BaseFragment
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.databinding.FragmentHomePageBinding
import com.example.dicodingintermediatesubmission1.presentation.addstory.AddStoryActivity
import com.example.dicodingintermediatesubmission1.presentation.detailstory.DetailStoryActivity
import com.example.dicodingintermediatesubmission1.presentation.homepage.adapter.HomePageAdapter
import com.example.dicodingintermediatesubmission1.presentation.homepage.adapter.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment :
    BaseFragment<FragmentHomePageBinding, HomePageViewModel>(FragmentHomePageBinding::inflate) {
    private lateinit var adapter: HomePageAdapter

    private val viewModelHome: HomePageViewModel by viewModels()

    override fun initView() {
        setupAdapter()
        addStory()
        setRefreshListener()
    }

    private fun setRefreshListener() {
        getViewBinding().srlRefreshRv.setOnRefreshListener {
            initData()
            getViewBinding().srlRefreshRv.isRefreshing = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState == null) {
            // Save data state to handle screen orientation
            initData()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    private fun setupAdapter() {
        adapter = HomePageAdapter(object : HomePageAdapter.StoryOnClickListener {
            override fun onClick(item: Story) {
                val intent = Intent(requireContext(), DetailStoryActivity::class.java)
                intent.putExtra(STORY_DATA, item)
                startActivity(intent)
            }

        })
        getViewBinding().rvStories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomePageFragment.adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    this@HomePageFragment.adapter.retry()
                }
            )
        }
    }

    private fun initData() {
        lifecycleScope.launchWhenStarted {
            viewModelHome.getStoriesList()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoadingRv.isVisible = isLoading
    }

    private val intentAddStory =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Refresh HomePage data
                initData()
            }
        }

    private fun addStory() {
        getViewBinding().fabAddStory.setOnClickListener {
            val intent = Intent(requireContext(), AddStoryActivity::class.java)
            intentAddStory.launch(intent)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModelHome.getStoriesResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    it.data?.let { result ->
                        adapter.submitData(lifecycle, result)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    companion object {
        const val STORY_DATA = "STORY_DATA"
    }

}