package com.example.dicodingintermediatesubmission1.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * FROM storyTable")
    fun getAllStories(): PagingSource<Int, Story>

    @Query("DELETE FROM storyTable")
    suspend fun deleteAll()
}