package com.example.dicodingintermediatesubmission1.data.local.datasource

import com.example.dicodingintermediatesubmission1.data.local.SessionPreference
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import javax.inject.Inject

interface LocalDataSource {
    fun getAuthToken(): String?
    fun setAuthToken(authToken: String?)
    fun isUserLoggedIn(): Boolean
    fun saveUserData(user: LoginResponse)
    fun getUserData(): LoginResponse?
    fun deleteSession()
}

class LocalDataSourceImpl
    @Inject constructor(private val sessionPreference: SessionPreference): LocalDataSource {
    override fun getAuthToken(): String? {
        return sessionPreference.authToken
    }

    override fun setAuthToken(authToken: String?) {
        sessionPreference.authToken = authToken
    }

    override fun isUserLoggedIn(): Boolean {
        return !sessionPreference.authToken.isNullOrEmpty()
    }

    override fun saveUserData(user: LoginResponse) {
        sessionPreference.user = user
    }

    override fun getUserData(): LoginResponse? {
        return sessionPreference.user
    }

    override fun deleteSession() {
        sessionPreference.deleteSession()
    }

}