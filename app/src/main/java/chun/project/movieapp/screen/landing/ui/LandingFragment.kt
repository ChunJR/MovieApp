package chun.project.movieapp.screen.landing.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import chun.project.movieapp.R
import chun.project.movieapp.databinding.FragmentLandingBinding
import chun.project.movieapp.model.ConfigResponseModel
import chun.project.movieapp.screen.MainActivity
import chun.project.movieapp.screen.home.ui.HomeFragment
import chun.project.movieapp.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingFragment : Fragment() {

    private val viewModel: LandingViewModel by viewModel()
    private var binding: FragmentLandingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDataChange()
        viewModel.getConfiguration()
    }

    private fun observeDataChange() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is LandingViewState.Error -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.txt_error)
                        .setMessage(it.message)
                        .setPositiveButton(R.string.txt_retry) { dialog, _ ->
                            viewModel.getConfiguration()
                            dialog.dismiss()
                        }
                        .show()
                }
                else -> {
                }
            }
        })
        viewModel.config.observe(viewLifecycleOwner, { configResponse ->
            val widthOfScreen = getWidthMetrics()
            handleData(configResponse, widthOfScreen)
        })
    }

    private fun handleData(configResponse: ConfigResponseModel?, widthOfScreen: Int) {
        configResponse?.let { config ->
            if (config.base_url?.isNotEmpty() == true) {
                requireContext().myAppPreferences[Constant.SHARED_PREFERENCE_IMAGE_URL] =
                    config.base_url!!
            }
            if (config.secure_base_url?.isNotEmpty() == true) {
                requireContext().myAppPreferences[Constant.SHARED_PREFERENCE_IMAGE_SECURE_URL] =
                    config.secure_base_url!!
            }
            if (config.poster_sizes?.isNotEmpty() == true) {
                val posterSize = getWidthSize(config.poster_sizes!!, widthOfScreen)
                requireContext().myAppPreferences[Constant.SHARED_PREFERENCE_IMAGE_POSTER_SIZE] =
                    posterSize
            }
            if (config.backdrop_sizes?.isNotEmpty() == true) {
                val backdropSize = getWidthSize(config.backdrop_sizes!!, widthOfScreen)
                requireContext().myAppPreferences[Constant.SHARED_PREFERENCE_IMAGE_BACKDROP_SIZE] =
                    backdropSize
            }
        }
        moveToHomeScreen()
    }

    private fun getWidthSize(sizeList: List<String>, widthOfScreen: Int): String {
        for (pos in sizeList.indices.reversed()) {
            val size = sizeList[pos]
            val result = size.filter { it.isDigit() }

            if (result.isNotEmpty()) {
                val widthResult = result.toInt()
                if (widthOfScreen - widthResult >= 0) {
                    return size
                }
            }
        }
        return ""
    }

    private fun getWidthMetrics(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    private fun moveToHomeScreen() {
        Handler().postDelayed({
            val homeFragment = HomeFragment.newInstance()
            (requireActivity() as MainActivity).replaceFragment(homeFragment, R.id.container)
        }, DELAY_TIME)
    }

    companion object {
        private const val DELAY_TIME = 500L

        @JvmStatic
        fun newInstance() = LandingFragment()
    }
}