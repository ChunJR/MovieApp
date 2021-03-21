package chun.project.movieapp.screen.home.ui

sealed class LandingViewState {
    object Loading: LandingViewState()
    object Success: LandingViewState()
    data class Error(val message: String): LandingViewState()
}