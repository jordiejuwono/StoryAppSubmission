package com.example.dicodingintermediatesubmission1.domain

import com.example.dicodingintermediatesubmission1.base.arch.BaseUseCase
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase
    @Inject constructor(private val repository: RemoteRepository, dispatcher: CoroutineDispatcher, private val localDataSource: LocalDataSource) : BaseUseCase<LoginUseCase.Param, LoginResponse>(dispatcher) {
    data class Param(
        val email: String,
        val password: String,
    )

    override suspend fun execute(param: Param?): Flow<Resource<LoginResponse>> {
        return flow {
            param?.let { p ->
                emit(Resource.Loading())
                try {
                    repository.loginUser(
                        email = p.email,
                        password = p.password
                    ).collect {
                        localDataSource.setAuthToken(it.loginResult?.token.toString())
                        localDataSource.saveUserData(it)
                        emit(Resource.Success(it))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e))
                }
            }

        }
    }
}