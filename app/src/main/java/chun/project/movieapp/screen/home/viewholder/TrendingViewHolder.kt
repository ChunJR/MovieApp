package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
import chun.project.movieapp.screen.home.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.viewholder_trending.view.*

class TrendingViewHolder(
    private val context: Context,
    private val view: View,
    private val listener: HomeListener,
    private val lifecycle: Lifecycle
) : HomeAdapter.HomeViewModelV2<List<MovieModel>>(view) {

    private val recyclerView = view.recyclerView

    override fun bind(viewType: Int, data: Pair<PagingData<MovieModel>, List<Genres>>) {
        val adapter = TrendingAdapter(listener)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        adapter.submitData(lifecycle, data.first)
    }
}