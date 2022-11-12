package com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory


import com.google.gson.annotations.SerializedName

data class SendStoryResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?
)