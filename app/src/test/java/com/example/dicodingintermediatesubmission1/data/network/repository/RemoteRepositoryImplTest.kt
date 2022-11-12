package com.example.dicodingintermediatesubmission1.data.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.dicodingintermediatesubmission1.MainDispatcherRule
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.local.database.StoryDao
import com.example.dicodingintermediatesubmission1.data.local.database.StoryDatabase
import com.example.dicodingintermediatesubmission1.data.local.database.StoryRemoteMediator
import com.example.dicodingintermediatesubmission1.data.network.datasource.RemoteDataSource
import com.example.dicodingintermediatesubmission1.data.network.model.request.AuthRequest
import com.example.dicodingintermediatesubmission1.data.network.model.response.auth.register.RegisterResponse
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.data.network.services.ApiServices
import com.example.dicodingintermediatesubmission1.presentation.homepage.StoriesPagingSource
import com.example.dicodingintermediatesubmission1.presentation.homepage.adapter.HomePageAdapter
import com.example.dicodingintermediatesubmission1.utils.ApiServicesDummy
import com.example.dicodingintermediatesubmission1.utils.DataDummies
import com.example.dicodingintermediatesubmission1.utils.RemoteDataSourceDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RemoteRepositoryImplTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var apiServices: ApiServices

    @Mock
    private lateinit var storiesDatabase: StoryDatabase

    private lateinit var remoteRepository: RemoteRepository

    private val email = "test@gmail.com"
    private val name = "test"
    private val password = "12345678"
    private val file = File("pathName")
    private val description = "description"
    private val lat = 6.5f
    private val lon = 6.5f

    @Before
    fun setup() {
        remoteDataSource = RemoteDataSourceDummy()
        apiServices = ApiServicesDummy()
        remoteRepository = RemoteRepositoryImpl(remoteDataSource, apiServices, storiesDatabase)
    }

    @Test
    fun `Should Return Register Response Success Message and Response is Not Null`() = runTest {
        val expectedResult = DataDummies.generateRegisterDummy()
        var successMessage = ""
        val actual = remoteRepository.registerUser(name, email, password)

        actual.collect {
            successMessage = it.message ?: ""
        }

        Assert.assertNotNull(actual)
        Assert.assertEquals(expectedResult.message, successMessage)
    }

    @Test
    fun `Should Return Login Response Success Token and Response is Not Null`() = runTest {
        val expectedResult = DataDummies.generateLoginDummy()
        var successMessage = ""
        var userToken = ""
        val actual = remoteRepository.loginUser(email, password)

        actual.collect {
            successMessage = it.message ?: ""
            userToken = it.loginResult?.token ?: ""
        }

        Assert.assertNotNull(actual)
        Assert.assertEquals(expectedResult.message, successMessage)
        Assert.assertEquals(expectedResult.loginResult?.token, userToken)
    }

    @Test
    fun `Should Return Upload Image Response Success Message When Uploading Story`() = runTest {
        val expectedResult = DataDummies.generateAddStoryDummy()
        var successMessage = ""
        val actual = remoteRepository.uploadImage(file, description, lat, lon)

        actual.collect {
            successMessage = it.message ?: ""
        }

        Assert.assertEquals(expectedResult.message, successMessage)
    }

    @Test
    fun `Get Stories Should Not Return Null and Return Same Length`() = runTest {
        val expectedResult = DataDummies.generateStoriesWithLocationDummy()
        val actual = remoteRepository.getStoriesListWithLocation()
        var listStorySize = 0

        actual.collect {
            listStorySize = it.listStory.size
        }

        Assert.assertNotNull(actual)
        Assert.assertEquals(expectedResult.listStory.size, listStorySize)
    }

    @Test
    fun `Get Paging Data Stories Should Not Return Null`() = runTest {
        val actual = remoteRepository.getStoriesList(1, 10)
        Assert.assertNotNull(actual)
    }
}