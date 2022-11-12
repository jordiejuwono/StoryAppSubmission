package com.example.dicodingintermediatesubmission1.domain

import com.example.dicodingintermediatesubmission1.data.local.repository.LocalRepository

class IsUserLoggedInUseCase(private val repository: LocalRepository){
    fun execute(): Boolean {
        return repository.isUserLoggedIn()
    }
}