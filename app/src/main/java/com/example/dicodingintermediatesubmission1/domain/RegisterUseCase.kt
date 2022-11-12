package com.example.dicodingintermediatesubmission1.domain

import com.example.dicodingintermediatesubmission1.base.arch.BaseUseCase
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterUseCase(
    private val repository: RemoteRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<RegisterUseCase.Param, RegisterResponse>(dispatcher) {
    data class Param(val name: String, val email: String, val password: String)

    override suspend fun execute(param: Param?): Flow<Resource<RegisterResponse>> {
        return flow {
            param?.let { p ->
                emit(Resource.Loading())
                try {
                    repository.registerUser(
                        name = p.name,
                        email = p.email,
                        password = p.password)
                        .collect {
                            emit(Resource.Success(data = it))
                        }
                } catch (e: Exception) {
                    emit(Resource.Error(e))
                }
            }
        }
    }
}