package com.example.dicodingintermediatesubmission1.di

import com.example.dicodingintermediatesubmission1.data.local.SessionPreference
import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSourceImpl
import com.example.dicodingintermediatesubmission1.data.network.datasource.RemoteDataSource
import com.example.dicodingintermediatesubmission1.data.network.datasource.RemoteDataSourceImpl
import com.example.dicodingintermediatesubmission1.data.network.services.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(sessionPreference: SessionPreference): LocalDataSource {
        return LocalDataSourceImpl(sessionPreference)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiServices: ApiServices): RemoteDataSource {
        return RemoteDataSourceImpl(apiServices)
    }
}