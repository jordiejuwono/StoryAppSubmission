package com.example.dicodingintermediatesubmission1.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.example.dicodingintermediatesubmission1.domain.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel
@Inject constructor(private val isUserLoggedInUseCase: IsUserLoggedInUseCase): ViewModel() {

    fun isUserLoggedIn():Boolean {
        return isUserLoggedInUseCase.execute()
    }
}