package com.example.dicodingintermediatesubmission1.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.domain.RegisterUseCase
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
class RegisterViewModelTest {
    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var registerUseCase: RegisterUseCase

    private lateinit var registerViewModel: RegisterViewModel

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val registerDummyResponse = DataDummies.generateRegisterDummy()
    private val name = "test"
    private val email = "test@gmail.com"
    private val password = "12345678"

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(registerUseCase)
    }

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when Register User Should Return Success and Not Null`() = runTest {
        val expectedRegisterResponse = registerDummyResponse
        val registerResponse = flowOf<Resource<RegisterResponse>>(Resource.Success(expectedRegisterResponse))

        `when`(registerUseCase.execute(RegisterUseCase.Param(name, email, password))).thenReturn(registerResponse)

        registerViewModel.registerUserSuspend(name, email, password).collect {
            Assert.assertNotNull(it)
            Assert.assertTrue(it is Resource.Success)
        }

        verify(registerUseCase).execute(RegisterUseCase.Param(name, email, password))

    }

    @Test
    fun `when Register User Failed Should Return Error`() = runTest {
        val registerResponse = flowOf<Resource<RegisterResponse>>(Resource.Error(Exception()))

        `when`(registerUseCase.execute(RegisterUseCase.Param(name, email, password))).thenReturn(registerResponse)

        registerViewModel.registerUserSuspend(name, email, password).collect {
            Assert.assertTrue(it is Resource.Error)
        }

        verify(registerUseCase).execute(RegisterUseCase.Param(name, email, password))

    }
}