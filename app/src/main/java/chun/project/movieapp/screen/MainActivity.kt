package chun.project.movieapp.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chun.project.movieapp.R
import chun.project.movieapp.screen.home.ui.LandingFragment
import chun.project.movieapp.util.replaceFragmentInActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewFragment()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.container)
            ?: replaceFragmentInActivity(LandingFragment.newInstance(), R.id.container)
    }
}