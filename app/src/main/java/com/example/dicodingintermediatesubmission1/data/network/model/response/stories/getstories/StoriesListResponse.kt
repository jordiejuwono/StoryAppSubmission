package com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories


import com.google.gson.annotations.SerializedName

data class StoriesListResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("listStory")
    var listStory: List<Story>,
    @SerializedName("message")
    var message: String?
)