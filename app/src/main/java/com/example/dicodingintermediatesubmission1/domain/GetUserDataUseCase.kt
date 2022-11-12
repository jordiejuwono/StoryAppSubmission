package com.example.dicodingintermediatesubmission1.domain

import com.example.dicodingintermediatesubmission1.data.local.repository.LocalRepository
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse

class GetUserDataUseCase(private val localRepository: LocalRepository) {
    fun execute(): LoginResponse? {
        return localRepository.getUserData()
    }
}