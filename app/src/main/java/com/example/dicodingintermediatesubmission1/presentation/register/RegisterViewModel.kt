package com.example.dicodingintermediatesubmission1.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(private val registerUseCase: RegisterUseCase): ViewModel() {
    val registerResult = MutableLiveData<Resource<RegisterResponse>>()

    fun registerUser(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            registerUserSuspend(name, email, password).collect {
                registerResult.postValue(it)
            }
        }
    }

    suspend fun registerUserSuspend(name: String, email: String, password: String): Flow<Resource<RegisterResponse>> = registerUseCase.execute(RegisterUseCase.Param(name, email, password))
}