package chun.project.movieapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.databinding.ItemTrendingMovieBinding
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.ui.HomeFragment
import chun.project.movieapp.util.Constant
import chun.project.movieapp.util.myAppPreferences
import chun.project.movieapp.util.px
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TrendingAdapterV2(
    private val context: Context,
    private val listener: HomeListener
) : PagingDataAdapter<MovieModel, TrendingAdapterV2.ViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingAdapterV2.ViewHolder {
        val binding = ItemTrendingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingAdapterV2.ViewHolder, position: Int) {
        val trending = getItem(position)
        trending?.let {
            val backdropUrl = getBackdropPath(trending.backdrop_path)

            Glide.with(context)
                .load(backdropUrl)
                .apply(RequestOptions().override(HomeFragment.IMG_TRENDING_WIDTH.px, HomeFragment.IMG_TRENDING_HEIGHT.px))
                .into(holder.binding.imageView)

            holder.itemView.setOnClickListener {
                listener.onTrendingClick(trending)
            }
        }
    }

    private fun getBackdropPath(backdropPath: String): String {
        val baseUrl = context.myAppPreferences.getString(
            Constant.SHARED_PREFERENCE_IMAGE_SECURE_URL,
            Constant.BASE_IMAGE_URL
        )
        val imageSize = context.myAppPreferences.getString(
            Constant.SHARED_PREFERENCE_IMAGE_BACKDROP_SIZE,
            Constant.DEFAULT_IMAGE_SIZE
        )

        return baseUrl + imageSize + backdropPath
    }

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

    inner class ViewHolder(val binding: ItemTrendingMovieBinding) :
        RecyclerView.ViewHolder(binding.root)
}
