package chun.project.movieapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.databinding.ItemTrendingMovieBinding
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.IMG_TRENDING_HEIGHT
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.IMG_TRENDING_WIDTH
import chun.project.movieapp.util.Constant
import chun.project.movieapp.util.myAppPreferences
import chun.project.movieapp.util.px
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TrendingAdapter(
    private val context: Context,
    private val trendingList: List<MovieModel>,
    private val listener: HomeListener
) :
    RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingAdapter.ViewHolder {
        val binding =
            ItemTrendingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trending = trendingList[position]
        val backdropUrl = getBackdropPath(trending.backdrop_path)

        Glide.with(context)
            .load(backdropUrl)
            .apply(RequestOptions().override(IMG_TRENDING_WIDTH.px, IMG_TRENDING_HEIGHT.px))
            .into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            listener.onTrendingClick(trending)
        }
    }

    override fun getItemCount(): Int {
        return trendingList.size
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

    inner class ViewHolder(val binding: ItemTrendingMovieBinding) :
        RecyclerView.ViewHolder(binding.root)
}