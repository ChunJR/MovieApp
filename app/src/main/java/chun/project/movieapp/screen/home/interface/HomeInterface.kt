package chun.project.movieapp.screen.home.`interface`

import chun.project.movieapp.model.MovieModel

interface HomeListener {
    fun onMovieClick(MovieModel: MovieModel)
}