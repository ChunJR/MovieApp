package chun.project.movieapp.screen.home.viewholder

import android.view.View
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.IMG_TRENDING_HEIGHT
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.IMG_TRENDING_WIDTH
import chun.project.movieapp.util.px


class LoadingViewHolder(
    private val view: View,
    private val listener: HomeListener
) : BaseViewHolder<List<MovieModel>>(view) {

    private val rootView = view.rootView

    override fun bind(data: Any) {
        val viewType = data as Int
        listener.onLoadMore(viewType)

        val layoutParams = rootView.layoutParams
        layoutParams.height = IMG_TRENDING_HEIGHT.px
        rootView.layoutParams = layoutParams
    }
}