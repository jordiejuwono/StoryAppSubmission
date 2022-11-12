package com.example.dicodingintermediatesubmission1.base.arch

import com.example.dicodingintermediatesubmission1.base.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<P, R: Any?> constructor(private val dispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(param: P? = null) : Flow<Resource<R>> {
        return execute(param)
            .catch { emit(Resource.Error(Exception(it))) }
            .flowOn(dispatcher)
    }

    @Throws(RuntimeException::class)
    abstract  suspend fun execute(param: P?= null): Flow<Resource<R>>
}