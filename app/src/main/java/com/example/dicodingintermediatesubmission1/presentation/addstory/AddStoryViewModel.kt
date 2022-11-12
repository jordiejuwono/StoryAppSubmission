package com.example.dicodingintermediatesubmission1.presentation.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse
import com.example.dicodingintermediatesubmission1.domain.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel
@Inject constructor(private val uploadImageUseCase: UploadImageUseCase) : ViewModel() {
    val uploadImageResult = MutableLiveData<Resource<SendStoryResponse>>()

    fun uploadImage(
        photoFile: File, description: String,
        lat: Float? = null, lon: Float? = null
    ) {
        viewModelScope.launch {
            uploadImageSuspend(photoFile, description, lat, lon).collect {
                uploadImageResult.value = it
            }
        }
    }

    suspend fun uploadImageSuspend(
        photoFile: File, description: String,
        lat: Float? = null, lon: Float? = null
    ): Flow<Resource<SendStoryResponse>> = uploadImageUseCase.execute(
        UploadImageUseCase.Param(
            photoFile, description, lat, lon
        )
    )
}