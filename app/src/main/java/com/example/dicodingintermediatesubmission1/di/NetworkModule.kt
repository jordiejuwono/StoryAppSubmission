package com.example.dicodingintermediatesubmission1.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.data.network.services.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(localDataSource: LocalDataSource, chuckerInterceptor: ChuckerInterceptor): ApiServices {
        return ApiServices.invoke(localDataSource, chuckerInterceptor)
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }
}