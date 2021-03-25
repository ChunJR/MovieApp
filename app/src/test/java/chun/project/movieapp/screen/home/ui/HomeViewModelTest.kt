package chun.project.movieapp.screen.home.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.repository.MovieRepo
import chun.project.movieapp.repository.MovieService
import chun.project.movieapp.repository.paging.MoviePagingSource
import chun.project.movieapp.util.RxImmediateSchedulerRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyZeroInteractions

class HomeViewModelTest {

    @get:Rule
    var rxRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var movieRepo: MovieRepo

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<HomeViewState>

    @MockK(relaxed = true)
    lateinit var categoriesObserver: Observer<List<Genres>>


    private lateinit var source: MoviePagingSource
    private val service: MovieService = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(movieRepo)
        source = MoviePagingSource("type", service)

        viewModel.state.observeForever(stateObserver)
        viewModel.categories.observeForever(categoriesObserver)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getMovies() {
        val onSuccess: Consumer<PagingSource.LoadResult<Int, MovieModel>> = mockk()
        val onError: Consumer<Throwable> = mockk()
        val params: PagingSource.LoadParams<Int> = mockk()

        val movieResponse = mockk<PagingData<MovieModel>>()

        every { movieRepo.getMovies("type") } returns Flowable.just(movieResponse)

        source.loadSingle(params).subscribe(onSuccess, onError)

        verify {
            service.getMovies("type", "apiKey", 1)
            onSuccess.accept(PagingSource.LoadResult.Page(listOf(), null, 2))
        }

        verifyZeroInteractions(onError)
    }

    @Test
    fun getCategoryList_success() {
        val categoriesResponse = categoriesMockData()

        every { movieRepo.getCategories() } returns Single.just(categoriesResponse)

        viewModel.getCategories()

        assertEquals(viewModel.categories.value, categoriesResponse.genresList)

        verifyOrder {
            movieRepo.getCategories()
            categoriesObserver.onChanged(categoriesResponse.genresList)
        }
    }

    @Test
    fun getCategoryList_error() {
        val errorMessage = "no internet"

        every { movieRepo.getCategories() } returns Single.error(Throwable(errorMessage))

        viewModel.getCategories()

        assertEquals(viewModel.categories.value, null)

        verifyOrder {
            movieRepo.getCategories()
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