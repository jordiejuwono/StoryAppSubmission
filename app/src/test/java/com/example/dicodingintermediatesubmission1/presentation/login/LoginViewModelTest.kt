package com.example.dicodingintermediatesubmission1.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.login.LoginResponse
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import com.example.dicodingintermediatesubmission1.domain.LoginUseCase
import com.example.dicodingintermediatesubmission1.getOrAwaitValue
import com.example.dicodingintermediatesubmission1.utils.DataDummies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginUseCase: LoginUseCase

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginUseCase)
    }

    private val loginDummyResponse = DataDummies.generateLoginDummy()
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val userEmail = "test@gmail.com"
    private val password = "12345678"

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when Login User Should Return Success and Not Null`() = runTest {
        val expectedLoginResponse = loginDummyResponse
        val loginResponse = flowOf<Resource<LoginResponse>>(Resource.Success(expectedLoginResponse))

        `when`(loginUseCase.execute(LoginUseCase.Param(userEmail, password))).thenReturn(loginResponse)

        loginViewModel.loginUserSuspend(userEmail, password).collect {
            Assert.assertNotNull(it)
            Assert.assertTrue(it is Resource.Success)
        }

        verify(loginUseCase).execute(LoginUseCase.Param(userEmail, password))

    }

    @Test
    fun `when Login Failed Should Return Error`() = runTest {
        val loginResponse = flowOf<Resource<LoginResponse>>(Resource.Error(Exception()))

        `when`(loginUseCase.execute(LoginUseCase.Param(userEmail, password))).thenReturn(loginResponse)

        loginViewModel.loginUserSuspend(userEmail, password).collect {
            Assert.assertTrue(it is Resource.Error)
        }

        verify(loginUseCase).execute(LoginUseCase.Param(userEmail, password))

    }
}