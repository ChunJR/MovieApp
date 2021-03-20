package chun.project.movieapp.screen.home.ui

sealed class HomeViewState {
    object Loading: HomeViewState()
    object Success: HomeViewState()
    data class Error(val message: String): HomeViewState()
}