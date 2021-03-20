package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.model.checkItemsAre
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.item_trending.view.*

class TrendingViewHolder(private val context: Context,
                         private val view: View,
                         private val listener: HomeListener) : BaseViewHolder<List<TrendingModel>>(view) {

    private lateinit var trendingList: List<TrendingModel>

    private val recyclerView = view.recyclerView

    override fun bind(data: List<Any>) {
        data.checkItemsAre<TrendingModel>()?.let {
            if (it.isNotEmpty()) {
                trendingList = it

                val adapter = TrendingAdapter(context, trendingList, listener)
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = adapter
            }
        }
    }
}