package chun.project.movieapp.screen.landing.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import chun.project.movieapp.model.ConfigResponseModel
import chun.project.movieapp.repository.MovieRepo
import chun.project.movieapp.util.RxImmediateSchedulerRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.*

class LandingViewModelTest {

    @get:Rule
    var rxRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var movileRepo: MovieRepo

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<LandingViewState>

    @MockK(relaxed = true)
    lateinit var configObserver: Observer<ConfigResponseModel>

    private lateinit var viewModel: LandingViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = LandingViewModel(movileRepo)

        viewModel.state.observeForever(stateObserver)
        viewModel.config.observeForever(configObserver)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getConfigList_success() {
        val configResponse = mockk<ConfigResponseModel>()

        every { movileRepo.getConfiguration() } returns Single.just(configResponse)

        viewModel.getConfiguration()

        Assert.assertEquals(viewModel.config.value, configResponse)

        verifyOrder {
            movileRepo.getConfiguration()
            configObserver.onChanged(configResponse)
        }
    }

    @Test
    fun getConfigList_error() {
        val errorMessage = "no internet"

        every { movileRepo.getConfiguration() } returns Single.error(Throwable(errorMessage))

        viewModel.getConfiguration()

        Assert.assertEquals(viewModel.config.value, null)

        verifyOrder {
            movileRepo.getConfiguration()
            stateObserver.onChanged(LandingViewState.Error(errorMessage))
        }
    }
}