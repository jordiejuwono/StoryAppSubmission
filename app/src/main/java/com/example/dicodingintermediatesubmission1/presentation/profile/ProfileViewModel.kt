package com.example.dicodingintermediatesubmission1.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.domain.GetUserDataUseCase
import com.example.dicodingintermediatesubmission1.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(private val getUserDataUseCase: GetUserDataUseCase, private val logoutUseCase: LogoutUseCase) : ViewModel() {
    fun getUserData(): LoginResponse? {
        return getUserDataUseCase.execute()
    }

    fun logOutUser() {
        logoutUseCase.execute()
    }
}