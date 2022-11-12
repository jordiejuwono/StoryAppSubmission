package com.example.dicodingintermediatesubmission1.di

import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.data.local.repository.LocalRepository
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import com.example.dicodingintermediatesubmission1.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideRegisterUseCase(repository: RemoteRepository): RegisterUseCase {
        return RegisterUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(repository: RemoteRepository, localDataSource: LocalDataSource) : LoginUseCase {
        return LoginUseCase(repository, Dispatchers.IO, localDataSource)
    }

    @Singleton
    @Provides
    fun provideGetStoriesUseCase(repository: RemoteRepository) : GetStoriesUseCase {
        return GetStoriesUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideUploadImageUseCase(repository: RemoteRepository) : UploadImageUseCase {
        return UploadImageUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideIsUserLoggedInUseCase(repository: LocalRepository) : IsUserLoggedInUseCase {
        return IsUserLoggedInUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUserDataUseCase(repository: LocalRepository) : GetUserDataUseCase {
        return GetUserDataUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideLogoutUseCase(repository: LocalRepository) : LogoutUseCase {
        return LogoutUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetStoriesListWithLocationUseCase(repository: RemoteRepository): GetStoriesListWithLocationUseCase {
        return GetStoriesListWithLocationUseCase(repository, Dispatchers.IO)
    }
}