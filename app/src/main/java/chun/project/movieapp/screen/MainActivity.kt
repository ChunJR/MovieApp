package chun.project.movieapp.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import chun.project.movieapp.R
import chun.project.movieapp.screen.landing.ui.LandingFragment
import chun.project.movieapp.util.replaceFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragment(LandingFragment.newInstance())
    }

    fun setupFragment(fragment: Fragment) {
        replaceFragment(fragment, R.id.container)
    }
}