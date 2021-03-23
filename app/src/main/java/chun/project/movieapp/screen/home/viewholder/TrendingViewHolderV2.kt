package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapterV2
import chun.project.movieapp.screen.home.adapter.TrendingAdapterV2
import kotlinx.android.synthetic.main.item_trending.view.*

class TrendingViewHolderV2(private val context: Context,
                           private val view: View,
                           private val listener: HomeListener,
                           private val lifecycle: Lifecycle
) : HomeAdapterV2.HomeViewModelV2<List<MovieModel>>(view) {

    private val recyclerView = view.recyclerView
    private val rootView = view.rootView

    override fun bind(data: PagingData<MovieModel>) {
            val adapter = TrendingAdapterV2(context, listener)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter

            adapter.submitData(lifecycle, data)
    }
}