package chun.project.movieapp.screen.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import chun.project.movieapp.RxImmediateSchedulerRule
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.model.TrendingResponseModel
import chun.project.movieapp.repository.MovieRepo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verifyOrder
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    var rxRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var movileRepo: MovieRepo

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<HomeViewState>

    @MockK(relaxed = true)
    lateinit var trendingObserver: Observer<List<TrendingModel>>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(movileRepo)

        viewModel.state.observeForever(stateObserver)
        viewModel.trending.observeForever(trendingObserver)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getTrendingList_success() {
        val trendingResponse = TrendingResponseModel(
            1,
            1000,
            10000,
            trendingMockData()
        )

        every { movileRepo.getTrendingList() } returns Single.just(trendingResponse)

        viewModel.addEvents()

        assertEquals(viewModel.trending.value, trendingResponse.results)
        assertEquals(viewModel.trendingPage, trendingResponse.page)

        verifyOrder {
            movileRepo.getTrendingList()

            stateObserver.onChanged(HomeViewState.Loading)
            stateObserver.onChanged(HomeViewState.Success)

            trendingObserver.onChanged(trendingResponse.results)
        }
    }

    @Test
    fun getTrendingList_error() {
        val errorMessage = "no internet"

        every { movileRepo.getTrendingList() } returns Single.error(Throwable(errorMessage))

        viewModel.addEvents()

        assertEquals(viewModel.trending.value, null)
        assertEquals(viewModel.trendingPage, 1)

        verifyOrder {
            movileRepo.getTrendingList()

            stateObserver.onChanged(HomeViewState.Loading)
            stateObserver.onChanged(HomeViewState.Error(errorMessage))
        }
    }

    private fun trendingMockData(): List<TrendingModel> {
        return arrayListOf(
            TrendingModel(1, "abc", "/fsafsa", "/fhsaufhsa"),
            TrendingModel(2, "xyz", "/xyz", "/xyz")
        )
    }

}