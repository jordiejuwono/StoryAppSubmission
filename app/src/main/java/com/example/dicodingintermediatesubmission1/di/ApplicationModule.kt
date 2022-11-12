package com.example.dicodingintermediatesubmission1.di

import android.content.Context
import androidx.room.Room
import com.example.dicodingintermediatesubmission1.data.local.SessionPreference
import com.example.dicodingintermediatesubmission1.data.local.database.StoryDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
    fun provideSessionPreferences(
        @ApplicationContext context: Context,
        gson: Gson
    ): SessionPreference {
        return SessionPreference(context, gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideStoryDatabase(@ApplicationContext context: Context): StoryDatabase {
        return StoryDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideStoryDao(database: StoryDatabase)
    = database.storyDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: StoryDatabase)
            = database.remoteKeysDao()
}