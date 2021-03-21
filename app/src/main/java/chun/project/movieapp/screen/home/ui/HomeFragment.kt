package chun.project.movieapp.screen.home.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.R
import chun.project.movieapp.databinding.FragmentHomeBinding
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
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
        viewModel.addEvents()
    }

    override fun onTrendingClick(trendingModel: TrendingModel) {
        Toast.makeText(requireContext(), "Trending click", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        homeAdapter = HomeAdapter(this)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = homeAdapter
    }

    private fun observeDataChange() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is HomeViewState.Loading -> {
                    showLoading()
                }
                else -> {
                    hideLoading()
                }
            }
        })
        viewModel.trending.observe(viewLifecycleOwner, {
            homeAdapter.updateTrendingList(it)
        })
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

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}