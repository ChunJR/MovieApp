package chun.project.movieapp.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chun.project.movieapp.R
import chun.project.movieapp.screen.landing.ui.LandingFragment
import chun.project.movieapp.util.popBackStack
import chun.project.movieapp.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = LandingFragment.newInstance()
        replaceFragment(fragment, R.id.container)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        popBackStack()
    }
}