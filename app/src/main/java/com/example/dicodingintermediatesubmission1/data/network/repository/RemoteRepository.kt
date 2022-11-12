package com.example.dicodingintermediatesubmission1.data.network.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.dicodingintermediatesubmission1.data.local.database.StoryDatabase
import com.example.dicodingintermediatesubmission1.data.local.database.StoryRemoteMediator
import com.example.dicodingintermediatesubmission1.data.network.datasource.RemoteDataSource
import com.example.dicodingintermediatesubmission1.data.network.model.request.AuthRequest
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse
import com.example.dicodingintermediatesubmission1.data.network.paging.StoryPagingSource
import com.example.dicodingintermediatesubmission1.data.network.services.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

interface RemoteRepository {
    suspend fun registerUser(name: String, email: String, password: String): Flow<RegisterResponse>

    suspend fun loginUser(email: String, password: String): Flow<LoginResponse>

    fun getStoriesList(page: Int, size: Int): Flow<PagingData<Story>>

    suspend fun uploadImage(
        photoFile: File,
        description: String,
        lat: Float?,
        lon: Float?
    ): Flow<SendStoryResponse>

    suspend fun getStoriesListWithLocation(): Flow<StoriesListResponse>
}

class RemoteRepositoryImpl
@Inject constructor(private val remoteDataSource: RemoteDataSource, private val api: ApiServices, private val storyDatabase: StoryDatabase) : RemoteRepository {
    override suspend fun registerUser(name: String, email: String, password: String): Flow<RegisterResponse> {
        return flow {
            emit(remoteDataSource.registerUser(AuthRequest(name = name, email = email, password = password)))
        }
    }

    override suspend fun loginUser(email: String, password: String): Flow<LoginResponse> {
        return flow {
            emit(remoteDataSource.loginUser(AuthRequest(email = email, password = password)))
        }
    }

    override fun getStoriesList(page: Int, size: Int): Flow<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = size
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, api),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).flow
    }

    override suspend fun uploadImage(
        photoFile: File,
        description: String,
        lat: Float?,
        lon: Float?
    ): Flow<SendStoryResponse> {
        return flow {
            emit(remoteDataSource.uploadImage(photoFile, description, lat, lon))
        }
    }

    override suspend fun getStoriesListWithLocation(): Flow<StoriesListResponse> {
        return flow {
            emit(remoteDataSource.getStoriesListWithLocation())
        }
    }

}