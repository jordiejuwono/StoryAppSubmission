package com.example.dicodingintermediatesubmission1.data.local.repository

import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import javax.inject.Inject

interface LocalRepository {
    fun getAuthToken(): String?
    fun setAuthToken(authToken: String?)
    fun isUserLoggedIn(): Boolean
    fun saveUserData(user: LoginResponse)
    fun getUserData(): LoginResponse?
    fun deleteSession()
}

class LocalRepositoryImpl
@Inject constructor(private val localDataSource: LocalDataSource) : LocalRepository {
    override fun getAuthToken(): String? {
        return localDataSource.getAuthToken()
    }

    override fun setAuthToken(authToken: String?) {
        localDataSource.setAuthToken(authToken)
    }

    override fun isUserLoggedIn(): Boolean {
        return localDataSource.isUserLoggedIn()
    }

    override fun saveUserData(user: LoginResponse) {
        localDataSource.saveUserData(user)
    }

    override fun getUserData(): LoginResponse? {
        return localDataSource.getUserData()
    }

    override fun deleteSession() {
        localDataSource.deleteSession()
    }

}