package chun.project.movieapp.screen.movie_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import chun.project.movieapp.R
import chun.project.movieapp.databinding.FragmentMovieDetailsBinding
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.util.Utils.formatReleaseDate
import chun.project.movieapp.util.Utils.getBackdropPath
import chun.project.movieapp.util.Utils.getPosterPath
import chun.project.movieapp.util.px
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailsBinding? = null
    private var movieModel: MovieModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { bundle ->
            getBundleData(bundle)
        } ?: run {
            arguments?.let { bundle ->
                getBundleData(bundle)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BUNDLE_MOVIE_MODEL, movieModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initBackButton()
        loadMovieImage()
        initMovieInfo()
    }

    private fun initBackButton() {
        binding?.ivBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun loadMovieImage() {
        binding?.let { binding ->
            val backdropUrl = getBackdropPath(
                requireContext(),
                movieModel?.backdrop_path,
                getOriginal = true
            )
            Glide.with(requireContext())
                .load(backdropUrl)
                .placeholder(R.drawable.bg_fake_view)
                .into(binding.ivMovieBackdrop)

            val posterUrl = getPosterPath(requireContext(), movieModel?.poster_path)
            Glide.with(requireContext())
                .load(posterUrl)
                .apply(RequestOptions().override(IMG_POSTER_WIDTH.px, IMG_POSTER_HEIGHT.px))
                .into(binding.ivMoviePoster)
        }
    }

    private fun initMovieInfo() {
        movieModel?.rating?.let {
            val star = it / 2
            binding?.tvRating?.text = star.toString()
            binding?.ratingBar?.rating = star
        }

        movieModel?.release_date?.let {
            binding?.tvReleaseDate?.text = formatReleaseDate(it)
        }

        movieModel?.title?.let {
            binding?.tvTitle?.text = it
        }

        movieModel?.overview?.let {
            binding?.tvOverview?.text = it
        }
    }

    private fun getBundleData(bundle: Bundle) {
        movieModel = bundle.getParcelable(BUNDLE_MOVIE_MODEL)
    }

    companion object {
        private const val BUNDLE_MOVIE_MODEL = "BUNDLE_MOVIE_MODEL"

        private const val IMG_POSTER_WIDTH = 120
        private const val IMG_POSTER_HEIGHT = 180

        fun newInstance(movie: MovieModel) = MovieDetailsFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(BUNDLE_MOVIE_MODEL, movie)
            }
            this.arguments = bundle
        }
    }
}