package com.example.dicodingintermediatesubmission1.presentation.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.domain.GetStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel
@Inject constructor(private val getStoriesUseCase: GetStoriesUseCase) : ViewModel() {
    private val _getStoriesResult = MutableLiveData<Resource<PagingData<Story>>>()
    val getStoriesResult get() = _getStoriesResult

    fun getStoriesList() {
        viewModelScope.launch {
            getStoriesListSuspend().collect {
                _getStoriesResult.postValue(it)
            }
        }
    }

    suspend fun getStoriesListSuspend(): Flow<Resource<PagingData<Story>>> = getStoriesUseCase.execute(
        coroutineScope = viewModelScope,
        param = GetStoriesUseCase.Param(
            page = 1,
            size = 10
        )
    )
}