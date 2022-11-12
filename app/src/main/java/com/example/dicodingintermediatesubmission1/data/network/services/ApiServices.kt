package com.example.dicodingintermediatesubmission1.data.network.services

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.dicodingintermediatesubmission1.BuildConfig
import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.data.network.model.request.AuthRequest
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiServices {

    @POST("register")
    suspend fun registerUser(@Body registerRequestBody: AuthRequest): RegisterResponse

    @POST("login")
    suspend fun loginUser(@Body loginRequestBody: AuthRequest): LoginResponse

    @GET("stories")
    suspend fun getStoriesList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoriesListResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float? = null,
        @Part("lon") lon: Float? = null
    ): SendStoryResponse

    @GET("stories?location=1")
    suspend fun getStoriesListWithLocation(): StoriesListResponse


    companion object {
        @JvmStatic
        operator fun invoke(localDataSource: LocalDataSource, chuckerInterceptor: ChuckerInterceptor): ApiServices {
            val authInterceptor = Interceptor {
                val requestBuilder = it.request().newBuilder()
                localDataSource.getAuthToken()?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                it.proceed(requestBuilder.build())
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_STORY_APP)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiServices::class.java)
        }
    }
}