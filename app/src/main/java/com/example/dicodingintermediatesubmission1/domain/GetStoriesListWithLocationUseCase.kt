package com.example.dicodingintermediatesubmission1.domain

import com.example.dicodingintermediatesubmission1.base.arch.BaseUseCase
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStoriesListWithLocationUseCase(private val repository: RemoteRepository, dispatcher: CoroutineDispatcher): BaseUseCase<Nothing, StoriesListResponse>(dispatcher) {
    override suspend fun execute(param: Nothing?): Flow<Resource<StoriesListResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                repository.getStoriesListWithLocation().collect {
                    emit(Resource.Success(it))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}