package chun.project.movieapp.screen.landing.ui

import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LandingViewStateTest {

    private val FAKE_ERROR = "this is my error"

    @MockK(relaxed = true)
    lateinit var mockContext: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun setStateError_shouldBeSettled() {
        // Given a mocked Context injected into the object under test...
        every { mockContext.getString(any()) } returns FAKE_ERROR

        // ..when set error with a string
        val state = LandingViewState.Error(FAKE_ERROR)

        // ...then the result should be the expected one.
        Assert.assertEquals(state.message, FAKE_ERROR)
    }
}