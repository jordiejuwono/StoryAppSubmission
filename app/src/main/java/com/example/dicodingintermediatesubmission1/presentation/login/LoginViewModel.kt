package com.example.dicodingintermediatesubmission1.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {
    val loginResult = MutableLiveData<Resource<LoginResponse>>()

    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            loginUserSuspend(email, password).collect {
                loginResult.postValue(it)
            }
        }
    }

    suspend fun loginUserSuspend(email: String, password: String): Flow<Resource<LoginResponse>> = loginUseCase.execute(LoginUseCase.Param(email, password))
}