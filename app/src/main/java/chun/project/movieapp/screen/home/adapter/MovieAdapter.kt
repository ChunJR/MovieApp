package chun.project.movieapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import chun.project.movieapp.databinding.ItemMovieBinding
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.ui.HomeFragment
import chun.project.movieapp.util.Constant
import chun.project.movieapp.util.myAppPreferences
import chun.project.movieapp.util.px
import com.bumptech.glide.Glide

class MovieAdapter(
    private val listener: HomeListener
) : PagingDataAdapter<MovieModel, MovieAdapter.ViewHolder>(
    COMPARATOR
) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        this.context = parent.context
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            val posterUrl = getPosterPath(movie.poster_path)
            Glide.with(context)
                .load(posterUrl)
                .into(holder.binding.ivMovie)

            val layoutParams = FrameLayout.LayoutParams(
                HomeFragment.IMG_MOVIES_WIDTH.px,
                HomeFragment.IMG_MOVIES_HEIGHT.px
            )
            holder.binding.ivMovie.layoutParams = layoutParams

            holder.binding.tvMovieName.text = movie.title

            holder.itemView.setOnClickListener {
                listener.onMovieClick(movie)
            }
        }
    }

    private fun getPosterPath(backdropPath: String): String {
        val baseUrl = context.myAppPreferences.getString(
            Constant.SHARED_PREFERENCE_IMAGE_SECURE_URL,
            Constant.BASE_IMAGE_URL
        )
        val imageSize = context.myAppPreferences.getString(
            Constant.SHARED_PREFERENCE_IMAGE_POSTER_SIZE,
            Constant.DEFAULT_IMAGE_SIZE
        )

        return baseUrl + imageSize + backdropPath
    }

//    private fun showProgress(holder: ViewHolder) {
//        holder.binding.progressBar.visibility = View.VISIBLE
//    }
//
//    private fun hideProgress(holder: ViewHolder) {
//        holder.binding.progressBar.visibility = View.GONE
//    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)
}
