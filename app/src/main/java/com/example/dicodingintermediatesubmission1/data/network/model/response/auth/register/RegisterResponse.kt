package com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?
)