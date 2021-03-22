package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.IMG_TRENDING_HEIGHT
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.IMG_TRENDING_WIDTH
import chun.project.movieapp.util.Constant
import chun.project.movieapp.util.myAppPreferences
import chun.project.movieapp.util.px
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_trending_movie.view.*

class TrendingViewHolder(
    private val context: Context,
    private val view: View,
    private val listener: HomeListener
) : BaseViewHolder<List<MovieModel>>(view) {

    private val imageView = view.imageView
    private val rootView = view.rootView

    override fun bind(data: Any) {
        val trending = data as MovieModel
        val backdropUrl = getBackdropPath(trending.backdrop_path)

        Glide.with(context)
            .load(backdropUrl)
            .apply(RequestOptions().override(IMG_TRENDING_WIDTH.px, IMG_TRENDING_HEIGHT.px))
            .into(imageView)

        rootView.setOnClickListener {
            listener.onTrendingClick(trending)
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
}