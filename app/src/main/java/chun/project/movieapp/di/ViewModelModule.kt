package chun.project.movieapp.di

import chun.project.movieapp.screen.home.ui.HomeViewModel
import chun.project.movieapp.screen.landing.ui.LandingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { LandingViewModel(get()) }
}