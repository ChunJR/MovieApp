package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
import chun.project.movieapp.screen.home.adapter.TrendingAdapter
import chun.project.movieapp.util.checkItemsAre
import kotlinx.android.synthetic.main.item_trending.view.*

class TrendingViewHolder(private val context: Context,
                         private val view: View,
                         private val listener: HomeListener) : HomeAdapter.HomeViewHolder<List<MovieModel>>(view) {

    private lateinit var trendingList: List<MovieModel>

    private val recyclerView = view.recyclerView
    private val rootView = view.rootView

    override fun bind(data: List<Any>) {
        val trendingList = data.checkItemsAre<MovieModel>()

        if (trendingList?.isNotEmpty() == true) {
            rootView.visibility = View.VISIBLE

            val adapter = TrendingAdapter(context, trendingList, listener)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        } else {
            rootView.visibility = View.GONE
        }
    }
}