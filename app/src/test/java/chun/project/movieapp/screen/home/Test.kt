package chun.project.movieapp.screen.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.repository.MovieRepo
import chun.project.movieapp.screen.home.ui.HomeViewModel
import chun.project.movieapp.screen.home.ui.HomeViewState
import chun.project.movieapp.util.RxImmediateSchedulerRule
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

class Test {
    @get:Rule
    var rxRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var movileRepo: MovieRepo

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<HomeViewState>

    @MockK(relaxed = true)
    lateinit var categoriesObserver: Observer<List<Genres>>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(movileRepo)

        viewModel.state.observeForever(stateObserver)
        viewModel.categories.observeForever(categoriesObserver)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getTrendingList_success() {
        val categoriesResponse = categoriesMockData()

        every { movileRepo.getCategories() } returns Single.just(categoriesResponse)

        viewModel.getCategories()

        assertEquals(viewModel.categories.value, categoriesResponse.genresList)

        verifyOrder {
            movileRepo.getCategories()

            categoriesObserver.onChanged(categoriesResponse.genresList)
        }
    }

    @Test
    fun getTrendingList_error() {
        val errorMessage = "no internet"

        every { movileRepo.getCategories() } returns Single.error(Throwable(errorMessage))

        viewModel.getCategories()

        assertEquals(viewModel.categories.value, null)

        verifyOrder {
            movileRepo.getCategories()
            stateObserver.onChanged(HomeViewState.Error(errorMessage))
        }
    }

    private fun categoriesMockData(): MovieModel {
        return MovieModel(
            12345,
            "Justin League",
            "Long time ago...",
            "/poster_path",
            "/backdrop_path",
            "2000-30-12",
            8f,
            arrayListOf(
                Genres(2421, "action")
            ),
        )
    }

}