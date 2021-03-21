package chun.project.movieapp.base

import android.app.Application
import chun.project.movieapp.di.dataModule
import chun.project.movieapp.di.networkModule
import chun.project.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            androidContext(this@BaseApplication)

            modules(
                listOf(
                    networkModule,
                    dataModule,
                    viewModelModule
                )
            )
        }
    }
}