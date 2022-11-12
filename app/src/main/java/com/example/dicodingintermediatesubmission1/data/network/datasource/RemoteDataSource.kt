package com.example.dicodingintermediatesubmission1.data.network.datasource

import com.example.dicodingintermediatesubmission1.data.network.model.request.AuthRequest
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse
import com.example.dicodingintermediatesubmission1.data.network.services.ApiServices
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun registerUser(registerRequestBody: AuthRequest): RegisterResponse

    suspend fun loginUser(loginRequestBody: AuthRequest): LoginResponse

    suspend fun getStoriesList(page: Int, size: Int): StoriesListResponse

    suspend fun uploadImage(
        photoFile: File,
        description: String,
        lat: Float?,
        lon: Float?
    ): SendStoryResponse

    suspend fun getStoriesListWithLocation(): StoriesListResponse
}

class RemoteDataSourceImpl
@Inject constructor(private val apiServices: ApiServices) : RemoteDataSource {
    override suspend fun registerUser(registerRequestBody: AuthRequest): RegisterResponse {
        return apiServices.registerUser(registerRequestBody)
    }

    override suspend fun loginUser(loginRequestBody: AuthRequest): LoginResponse {
        return apiServices.loginUser(loginRequestBody)
    }

    override suspend fun getStoriesList(page: Int, size: Int): StoriesListResponse {
        return apiServices.getStoriesList(page, size)
    }

    override suspend fun uploadImage(photoFile: File, description: String, lat: Float?, lon: Float?): SendStoryResponse {
        val requestImageFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageBodyBuilder : MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            photoFile.name,
            requestImageFile
        )

        val descriptionText = description.toRequestBody("text/plain".toMediaType())
        return apiServices.uploadImage(imageBodyBuilder, descriptionText, lat, lon)
    }

    override suspend fun getStoriesListWithLocation(): StoriesListResponse {
        return apiServices.getStoriesListWithLocation()
    }

}