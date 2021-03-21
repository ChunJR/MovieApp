package chun.project.movieapp.screen.landing.ui

sealed class LandingViewState {
    object Loading: LandingViewState()
    object Success: LandingViewState()
    data class Error(val message: String): LandingViewState()
}