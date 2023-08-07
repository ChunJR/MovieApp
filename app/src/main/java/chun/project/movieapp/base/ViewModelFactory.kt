package chun.project.movieapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import chun.project.movieapp.repository.MovieRepo
import chun.project.movieapp.screen.landing.ui.LandingViewModel

class ViewModelFactory(private val MovieRepoImpl: MovieRepo) : ViewModelProvider.Factory {

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LandingViewModel::class.java)) {
//            return LandingViewModel(MainRepository(MovieRepoImpl)) as T
//        }
//        throw IllegalArgumentException("Unknown class name")
//    }

}