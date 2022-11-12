package com.example.dicodingintermediatesubmission1.presentation.mapfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.domain.GetStoriesListWithLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject constructor(private val getStoriesListWithLocationUseCase: GetStoriesListWithLocationUseCase): ViewModel() {
        val getStoriesWithLocationResult = MutableLiveData<Resource<StoriesListResponse>>()

        fun getStoriesWithLocation() {
            viewModelScope.launch {
                getStoriesWithLocationSuspend().collect {
                    getStoriesWithLocationResult.postValue(it)
                }
            }
        }

        suspend fun getStoriesWithLocationSuspend(): Flow<Resource<StoriesListResponse>> = getStoriesListWithLocationUseCase.execute()
    }