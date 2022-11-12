package com.example.dicodingintermediatesubmission1.presentation.homepage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.dicodingintermediatesubmission1.MainDispatcherRule
import com.example.dicodingintermediatesubmission1.base.model.Resource
import com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories.Story
import com.example.dicodingintermediatesubmission1.data.network.paging.StoryPagingSource
import com.example.dicodingintermediatesubmission1.data.network.repository.RemoteRepository
import com.example.dicodingintermediatesubmission1.domain.GetStoriesUseCase
import com.example.dicodingintermediatesubmission1.domain.UploadImageUseCase
import com.example.dicodingintermediatesubmission1.getOrAwaitValue
import com.example.dicodingintermediatesubmission1.presentation.homepage.adapter.HomePageAdapter
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
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomePageViewModelTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getStoriesUseCase: GetStoriesUseCase

    private lateinit var storiesViewModel: HomePageViewModel

    private val dummyStories = DataDummies.generateStoriesDummy()

    val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?){}
    }

    @Before
    fun setUp() {
        storiesViewModel = HomePageViewModel(getStoriesUseCase)
    }


    @Test
    fun `when Get Stories With Paging Should Not Null and Return Success`() = runTest {
        val dummyStories = DataDummies.generateDummyStoriesPagination()
        val data: PagingData<Story> = StoriesPagingSource.snapshot(dummyStories)
        val expectedResult = flowOf<Resource<PagingData<Story>>>(Resource.Success(data))

        `when`(getStoriesUseCase.execute(GetStoriesUseCase.Param(1, 10), storiesViewModel.viewModelScope)).thenReturn(expectedResult)

        storiesViewModel.getStoriesListSuspend().collect {
            val differ = AsyncPagingDataDiffer(
                diffCallback = HomePageAdapter.differCallback,
                updateCallback = noopListUpdateCallback,
                workerDispatcher = Dispatchers.Main
            )
            differ.submitData(it.data!!)

            Assert.assertNotNull(differ.snapshot())
            Assert.assertEquals(dummyStories, differ.snapshot())
            Assert.assertTrue(it is Resource.Success)
            Assert.assertEquals(dummyStories.size, differ.snapshot().size)
        }
    }

    @Test
    fun `when Get Stories Failed Should Return Error`() {
        runTest {
            val expectedResult = flowOf<Resource<PagingData<Story>>>(Resource.Error(Exception()))

            `when`(getStoriesUseCase.execute(GetStoriesUseCase.Param(1, 10), storiesViewModel.viewModelScope)).thenReturn(expectedResult)

            storiesViewModel.getStoriesListSuspend().collect {
                Assert.assertTrue(it is Resource.Error)
            }
        }
    }


}

class StoriesPagingSource : PagingSource<Int, LiveData<List<Story>>>() {

    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

}
