package com.example.dicodingintermediatesubmission1.domain

import com.example.dicodingintermediatesubmission1.base.arch.BaseUseCase
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class UploadImageUseCase(private val repository: RemoteRepository, dispatcher: CoroutineDispatcher): BaseUseCase<UploadImageUseCase.Param, SendStoryResponse>(dispatcher) {
    data class Param(
        val photoFile: File, val description: String, val lat: Float?, val lon: Float?
    )

    override suspend fun execute(param: Param?): Flow<Resource<SendStoryResponse>> {
        return flow {
            param?.let { p ->
                emit(Resource.Loading())
                try {
                    repository.uploadImage(photoFile = p.photoFile, description = p.description, lat = p.lat, lon = p.lon).collect {
                        emit(Resource.Success(it))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e))
                }
            }

        }
    }
}