package chun.project.movieapp.di

import chun.project.movieapp.repository.GetMoviesRxRepository
import chun.project.movieapp.repository.GetMoviesRxRepositoryImpl
import chun.project.movieapp.repository.MovieRepo
import chun.project.movieapp.repository.MovieRepoImpl
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepo> { MovieRepoImpl( get() ) }
    single<GetMoviesRxRepository> { GetMoviesRxRepositoryImpl( get() ) }
}