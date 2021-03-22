package chun.project.movieapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.viewholder.LoadingViewHolder
import chun.project.movieapp.screen.home.viewholder.TrendingViewHolder


class TrendingAdapter(
    private val trendingList: List<MovieModel>,
    private val listener: HomeListener,
    private val viewType: Int
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_ITEM -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_trending_movie, parent, false)
                TrendingViewHolder(context, view, listener)
            }
            TYPE_LOADING -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view, listener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (trendingList[position].id != -1) {
            holder.bind(trendingList[position])
        } else {
            holder.bind(viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (trendingList[position].id != -1) {
            return TYPE_ITEM
        } else {
            return TYPE_LOADING
        }
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
    }
}