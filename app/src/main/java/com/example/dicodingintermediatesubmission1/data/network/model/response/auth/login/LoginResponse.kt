package com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("loginResult")
    var loginResult: LoginResult?,
    @SerializedName("message")
    var message: String?
)