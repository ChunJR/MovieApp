package chun.project.movieapp.screen.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.model.TrendingResponseModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import kotlinx.android.synthetic.main.item_trending.view.*

class TrendingViewHolder(val view: View,
                         val listener: HomeListener,
                         val data: List<TrendingModel>) : BaseViewHolder<List<TrendingModel>>(view), View.OnClickListener {

    val textView = view.textView

    override fun bind(trendingList: List<TrendingModel>) {
        textView.text = trendingList[0].title

//        trailerListView!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition

        if (position < 0) {
            return
        }

//        val trailer = data[position] as Trailer
//        listener.onWatch(trailer, adapterPosition)
    }
}