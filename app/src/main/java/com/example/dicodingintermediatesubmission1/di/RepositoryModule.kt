package com.example.dicodingintermediatesubmission1.di

import com.example.dicodingintermediatesubmission1.data.local.database.StoryDatabase
import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.data.local.repository.LocalRepository
import com.example.dicodingintermediatesubmission1.data.local.repository.LocalRepositoryImpl
import com.example.dicodingintermediatesubmission1.data.network.datasource.RemoteDataSource
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepositoryImpl
import com.example.dicodingintermediatesubmission1.data.network.services.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLocalRepository(localDataSource: LocalDataSource): LocalRepository {
        return LocalRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(remoteDataSource: RemoteDataSource, api: ApiServices, storyDatabase: StoryDatabase): RemoteRepository {
        return RemoteRepositoryImpl(remoteDataSource, api, storyDatabase)
    }
}