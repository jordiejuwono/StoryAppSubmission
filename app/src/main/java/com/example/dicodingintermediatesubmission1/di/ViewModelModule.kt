package com.example.dicodingintermediatesubmission1.di

import com.example.dicodingintermediatesubmission1.base.arch.GenericViewModelFactory
import com.example.dicodingintermediatesubmission1.data.local.datasource.LocalDataSource
import com.example.dicodingintermediatesubmission1.domain.*
import com.example.dicodingintermediatesubmission1.presentation.addstory.AddStoryViewModel
import com.example.dicodingintermediatesubmission1.presentation.homepage.HomePageViewModel
import com.example.dicodingintermediatesubmission1.presentation.login.LoginViewModel
import com.example.dicodingintermediatesubmission1.presentation.mapfragment.MapViewModel
import com.example.dicodingintermediatesubmission1.presentation.profile.ProfileViewModel
import com.example.dicodingintermediatesubmission1.presentation.register.RegisterViewModel
import com.example.dicodingintermediatesubmission1.presentation.splashscreen.SplashScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    @ActivityScoped
    fun provideRegisterViewModel(registerUseCase: RegisterUseCase): RegisterViewModel {
        return GenericViewModelFactory(RegisterViewModel(registerUseCase)).create(
            RegisterViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideLoginViewModel(loginUseCase: LoginUseCase): LoginViewModel {
        return GenericViewModelFactory(LoginViewModel(loginUseCase)).create(
            LoginViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideHomePageViewModel(getStoriesUseCase: GetStoriesUseCase): HomePageViewModel {
        return GenericViewModelFactory(HomePageViewModel(getStoriesUseCase)).create(
            HomePageViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideAddStoryViewModel(uploadImageUseCase: UploadImageUseCase): AddStoryViewModel {
        return GenericViewModelFactory(AddStoryViewModel(uploadImageUseCase)).create(
            AddStoryViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideSplashScreenViewModel(isUserLoggedInUseCase: IsUserLoggedInUseCase): SplashScreenViewModel {
        return GenericViewModelFactory(SplashScreenViewModel(isUserLoggedInUseCase)).create(
            SplashScreenViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideProfileViewModel(getUserDataUseCase: GetUserDataUseCase, logoutUseCase: LogoutUseCase): ProfileViewModel {
        return GenericViewModelFactory(ProfileViewModel(getUserDataUseCase, logoutUseCase)).create(
            ProfileViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideMapViewModel(getStoriesListWithLocationUseCase: GetStoriesListWithLocationUseCase): MapViewModel {
        return GenericViewModelFactory(MapViewModel(getStoriesListWithLocationUseCase)).create(
            MapViewModel::class.java
        )
    }
}