package chun.project.movieapp.screen.movie_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import chun.project.movieapp.databinding.FragmentMovieDetailsBinding
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.util.Utils.getBackdropPath
import chun.project.movieapp.util.Utils.getPosterPath
import com.bumptech.glide.Glide

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

        binding?.let { binding ->

            movieModel?.backdrop_path?.let {
                val backdropUrl = getBackdropPath(requireContext(), it)
                movieModel?.let {
                    Glide.with(requireContext())
                        .load(backdropUrl)
                        .into(binding.ivMovieBackdrop)
                }
            }

//            movieModel?.poster_path?.let {
//                val posterUrl = getPosterPath(requireContext(), it)
//                movieModel?.let {
//                    Glide.with(requireContext())
//                        .load(posterUrl)
//                        .into(binding.ivMoviePoster)
//                }
//            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BUNDLE_MOVIE_MODEL, movieModel)
    }

    private fun getBundleData(bundle: Bundle) {
        movieModel = bundle.getParcelable(BUNDLE_MOVIE_MODEL)
    }

    companion object {
        private const val BUNDLE_MOVIE_MODEL = "BUNDLE_MOVIE_MODEL"

        fun newInstance(movie: MovieModel) = MovieDetailsFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(BUNDLE_MOVIE_MODEL, movie)
            }
            this.arguments = bundle
        }
    }
}