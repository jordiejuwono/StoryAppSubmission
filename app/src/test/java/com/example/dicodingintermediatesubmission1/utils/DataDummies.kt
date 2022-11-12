package com.example.dicodingintermediatesubmission1.utils

import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResult
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse

object DataDummies {
    fun generateAddStoryDummy(): SendStoryResponse {
        return SendStoryResponse(
            error = false,
            message = "Story created successfully"
        )
    }

    fun generateAddStoryFailedDummy(): SendStoryResponse {
        return SendStoryResponse(
            error = true,
            message = "Add story failed"
        )
    }

    fun generateStoriesDummy(): List<Story> {
        val storiesList = ArrayList<Story>()
        for (i in 0..10) {
            val story = Story(
                id = "id",
                name = "name",
                description = "description",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos",
                createdAt = "2022-10-10T00:00:00:000Z",
                lat = 6.5,
                lon = 6.5
            )
            storiesList.add(story)
        }
        return storiesList
    }

    fun generateDummyStoriesPagination(): List<Story> {
        val storiesList: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                id = "id",
                name = "name",
                description = "description",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos",
                createdAt = "2022-10-10T00:00:00:000Z",
                lat = 6.5,
                lon = 6.5
            )
            storiesList.add(story)
        }
        return storiesList
    }

    fun generateLoginDummy(): LoginResponse {
        return LoginResponse(
            error = false,
            message = "success",
            loginResult =
            LoginResult(
                name = "name",
                userId = "user",
                token = "abcde"
            )
        )
    }

    fun generateStoriesWithLocationDummy(): StoriesListResponse {
        val storiesList = ArrayList<Story>()
        for (i in 0..10) {
            val story = Story(
                id = "id",
                name = "name",
                description = "description",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos",
                createdAt = "2022-10-10T00:00:00:000Z",
                lat = 6.5,
                lon = 6.5
            )
            storiesList.add(story)
        }
        return StoriesListResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = storiesList
        )
    }

    fun generateRegisterDummy(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "User created"
        )
    }

}