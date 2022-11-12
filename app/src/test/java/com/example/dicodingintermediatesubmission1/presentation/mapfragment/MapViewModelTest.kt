package com.example.dicodingintermediatesubmission1.presentation.mapfragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.StoriesListResponse
import com.example.dicodingintermediatesubmission1.domain.GetStoriesListWithLocationUseCase
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
class MapViewModelTest {
    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getStoriesListWithLocationUseCase: GetStoriesListWithLocationUseCase

    private lateinit var mapViewModel: MapViewModel

    private val storiesDummy = DataDummies.generateStoriesWithLocationDummy()

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mapViewModel = MapViewModel(getStoriesListWithLocationUseCase)
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
    fun `when Get Stories Should Not Null and Return Success`() {
        runTest {
            val expectedResult = flowOf<Resource<StoriesListResponse>>(Resource.Success(storiesDummy))

            `when`(getStoriesListWithLocationUseCase.execute()).thenReturn(expectedResult)

            mapViewModel.getStoriesWithLocationSuspend().collect {
                Assert.assertTrue(it is Resource.Success)
                Assert.assertNotNull(it)
            }

            verify(getStoriesListWithLocationUseCase).execute()

        }
    }

    @Test
    fun `when Get Stories Failed Should Return Error`() {
        runTest {
            val expectedResult = flowOf<Resource<StoriesListResponse>>(Resource.Error(Exception()))

            `when`(getStoriesListWithLocationUseCase.execute()).thenReturn(expectedResult)

            mapViewModel.getStoriesWithLocationSuspend().collect {
                Assert.assertTrue(it is Resource.Error)
            }

            verify(getStoriesListWithLocationUseCase).execute()

        }
    }
}