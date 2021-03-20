package chun.project.movieapp.screen.home

sealed class HomeViewState {
    object Loading: HomeViewState()
    object Success: HomeViewState()
    data class Error(val message: String): HomeViewState()
}