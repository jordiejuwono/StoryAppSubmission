package com.example.dicodingintermediatesubmission1.domain

import com.example.dicodingintermediatesubmission1.data.local.repository.LocalRepository

class LogoutUseCase(private val repository: LocalRepository) {
    fun execute() {
        repository.deleteSession()
    }
}