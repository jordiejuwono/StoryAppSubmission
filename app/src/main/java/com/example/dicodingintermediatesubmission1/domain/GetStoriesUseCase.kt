package com.example.dicodingintermediatesubmission1.domain

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dicodingintermediatesubmission1.base.arch.BaseUseCase
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStoriesUseCase(private val repository: RemoteRepository, dispatcher: CoroutineDispatcher) {
    suspend fun execute(param: Param?, coroutineScope: CoroutineScope): Flow<Resource<PagingData<Story>>> {
        return flow {
            emit(Resource.Loading())
            try {
                repository.getStoriesList(page = param?.page ?: 1, size = param?.size ?: 10).cachedIn(coroutineScope)
                    .collect {
                        emit(Resource.Success(it))
                    }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }

    data class Param(
        val page: Int?,
        val size: Int?
    )

}