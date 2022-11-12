package com.example.dicodingintermediatesubmission1.utils

import com.example.dicodingintermediatesubmission1.data.network.datasource.RemoteDataSource
import com.example.dicodingintermediatesubmission1.data.network.model.request.AuthRequest
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse
import java.io.File

class RemoteDataSourceDummy: RemoteDataSource {
    override suspend fun registerUser(registerRequestBody: AuthRequest): RegisterResponse {
        return DataDummies.generateRegisterDummy()
    }

    override suspend fun loginUser(loginRequestBody: AuthRequest): LoginResponse {
        return DataDummies.generateLoginDummy()
    }

    override suspend fun getStoriesList(page: Int, size: Int): StoriesListResponse {
        return DataDummies.generateStoriesWithLocationDummy()
    }

    override suspend fun uploadImage(
        photoFile: File,
        description: String,
        lat: Float?,
        lon: Float?
    ): SendStoryResponse {
        return DataDummies.generateAddStoryDummy()
    }

    override suspend fun getStoriesListWithLocation(): StoriesListResponse {
        return DataDummies.generateStoriesWithLocationDummy()
    }
}