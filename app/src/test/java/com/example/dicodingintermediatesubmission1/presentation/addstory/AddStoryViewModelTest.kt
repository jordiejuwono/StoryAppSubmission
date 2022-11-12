package com.example.dicodingintermediatesubmission1.presentation.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.sendstory.SendStoryResponse
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import com.example.dicodingintermediatesubmission1.domain.UploadImageUseCase
import com.example.dicodingintermediatesubmission1.getOrAwaitValue
import com.example.dicodingintermediatesubmission1.utils.DataDummies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var addStoryUseCase: UploadImageUseCase

    private lateinit var addStoryViewModel: AddStoryViewModel

    private val addStoryDummy = DataDummies.generateAddStoryDummy()

    private val addStoryFailedDummy = DataDummies.generateAddStoryFailedDummy()

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(addStoryUseCase)
    }

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    private val file: File = File("pathName")
    private val description = "description"
    private val lat = 1.2f
    private val lon = 1.2f

    @Test
    fun `when Upload Story Success Should Return Message Success and Response Should Not be Null`() {
        runTest {
            val expectedResult = flowOf<Resource<SendStoryResponse>>(Resource.Success(addStoryDummy))
            var successMessage = ""

            `when`(addStoryUseCase.execute(UploadImageUseCase.Param(file, description, lat, lon))).thenReturn(expectedResult)

            expectedResult.collect {
                successMessage = it.data?.message ?: ""
            }

            addStoryViewModel.uploadImageSuspend(file, description, lat, lon).collect {
                Assert.assertNotNull(it.data)
                Assert.assertEquals(successMessage, it.data?.message)
            }

            verify(addStoryUseCase).execute(UploadImageUseCase.Param(file, description, lat, lon))
        }
    }

    @Test
    fun `when Upload Story Failed Should Return Error`() {
        runTest {
            val expectedResult = flowOf<Resource<SendStoryResponse>>(Resource.Error(Exception(), addStoryFailedDummy))
            var failedMessage = ""

            `when`(addStoryUseCase.execute(UploadImageUseCase.Param(file, description, lat, lon))).thenReturn(expectedResult)

            expectedResult.collect {
                failedMessage = it.data?.message ?: ""
            }

            addStoryViewModel.uploadImageSuspend(file, description, lat, lon).collect {
                Assert.assertTrue(it is Resource.Error)
                Assert.assertEquals(failedMessage, it.data?.message)
                Assert.assertTrue(it.data?.error == true)
            }

            verify(addStoryUseCase).execute(UploadImageUseCase.Param(file, description, lat, lon))
        }
    }
}