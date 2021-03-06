package chun.project.movieapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.databinding.ItemTrendingBinding
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.ui.HomeFragment
import chun.project.movieapp.util.Constant
import chun.project.movieapp.util.Utils.getBackdropPath
import chun.project.movieapp.util.myAppPreferences
import chun.project.movieapp.util.px
import com.bumptech.glide.Glide

class TrendingAdapter(
    private val listener: HomeListener
) : PagingDataAdapter<MovieModel, TrendingAdapter.ViewHolder>(
    COMPARATOR
) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingAdapter.ViewHolder {
        this.context = parent.context
        val binding = ItemTrendingBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingAdapter.ViewHolder, position: Int) {
        val trending = getItem(position)
        trending?.let {
            val backdropUrl = getBackdropPath(context, trending.backdrop_path)
            Glide.with(context)
                .load(backdropUrl)
                .into(holder.binding.imageView)

            val layoutParams = FrameLayout.LayoutParams(
                HomeFragment.IMG_TRENDING_WIDTH.px,
                HomeFragment.IMG_TRENDING_HEIGHT.px
            )
            holder.binding.imageView.layoutParams = layoutParams

            holder.itemView.setOnClickListener {
                listener.onMovieClick(trending)
            }
        }
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

    inner class ViewHolder(val binding: ItemTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)
}
