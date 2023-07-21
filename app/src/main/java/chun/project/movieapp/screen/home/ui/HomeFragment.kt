package chun.project.movieapp.screen.home.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.R
import chun.project.movieapp.databinding.FragmentHomeBinding
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.MainActivity
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_CATEGORY
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_POPULAR
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_TOP_RATED
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_TRENDING
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_UPCOMING
import chun.project.movieapp.screen.movie_details.ui.MovieDetailsFragment
import chun.project.movieapp.util.addFragmentToBackStack
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), HomeListener {

    private val viewModel: HomeViewModel by viewModel()
    private var binding: FragmentHomeBinding? = null

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeDataChange()
        fetchData()
    }

    override fun onMovieClick(movie: MovieModel) {
        movie.id.let {
            viewModel.getMovieDetails(it)
        }
    }

    private fun initView() {
        homeAdapter = HomeAdapter(lifecycle, this)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = homeAdapter

        binding?.swipeRefresh?.setOnRefreshListener {
            fetchData()
        }
    }

    private fun observeDataChange() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is HomeViewState.Loading -> {
                    showLoading()
                }

                is HomeViewState.Error -> {
                    hideLoading()
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.txt_error)
                        .setMessage(it.message)
                        .setPositiveButton(R.string.txt_ok) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }

                else -> {
                    hideLoading()
                }
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            if (binding?.swipeRefresh?.isRefreshing == true) {
                binding?.swipeRefresh?.isRefreshing = false
            }
            homeAdapter.updateCategories(POSITION_CATEGORY, it)
        }
        viewModel.movieModel.observe(viewLifecycleOwner) { movie ->
            val detailsFragment = MovieDetailsFragment.newInstance(movie)
            (requireActivity() as MainActivity).addFragmentToBackStack(
                detailsFragment,
                R.id.container
            )
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchData() {
        viewModel.getTrendingMovies().subscribe {
            homeAdapter.submitData(POSITION_TRENDING, it)
        }
        viewModel.getMovies(TYPE_POPULAR).subscribe {
            homeAdapter.submitData(POSITION_POPULAR, it)
        }
        viewModel.getMovies(TYPE_TOP_RATED).subscribe {
            homeAdapter.submitData(POSITION_TOP_RATED, it)
        }
        viewModel.getMovies(TYPE_UPCOMING).subscribe {
            homeAdapter.submitData(POSITION_UPCOMING, it)
        }
        viewModel.getCategories()
    }

    private fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding?.progressBar?.visibility = View.GONE
    }

    companion object {
        const val IMG_TRENDING_WIDTH = 300
        const val IMG_TRENDING_HEIGHT = 160

        const val IMG_MOVIES_WIDTH = 140
        const val IMG_MOVIES_HEIGHT = 210

        const val CATEGORY_WIDTH = 140
        const val CATEGORY_HEIGHT = 75

        private const val TYPE_POPULAR = "popular"
        private const val TYPE_TOP_RATED = "top_rated"
        private const val TYPE_UPCOMING = "upcoming"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}