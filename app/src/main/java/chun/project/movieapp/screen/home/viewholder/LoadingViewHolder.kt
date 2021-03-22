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
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_trending_movie.view.*

class LoadingViewHolder(
    private val view: View,
    private val listener: HomeListener
) : BaseViewHolder<List<MovieModel>>(view) {

    override fun bind(data: Any) {
        val viewType = data as Int
        listener.onLoadMore(viewType)
    }
}