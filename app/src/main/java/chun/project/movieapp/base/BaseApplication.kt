package chun.project.movieapp.base

import android.app.Application
import chun.project.movieapp.di.dataModule
import chun.project.movieapp.di.networkModule
import chun.project.movieapp.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}