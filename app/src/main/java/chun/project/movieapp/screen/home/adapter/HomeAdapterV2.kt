package chun.project.movieapp.screen.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.model.HomeDataV2
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.viewholder.TrendingViewHolderV2

class HomeAdapterV2(private val lifecycle: Lifecycle, private val listener: HomeListener) :
    RecyclerView.Adapter<HomeAdapterV2.HomeViewModelV2<*>>() {

    private val data: MutableList<HomeDataV2> = arrayListOf()

    companion object {
        const val TYPE_TRENDING = 0
        const val TYPE_CATEGORY = 1
        const val TYPE_MOVIE = 2

        const val POSITION_TRENDING = 0
        const val POSITION_CATEGORY = 1
        const val POSITION_POPULAR = 2
        const val POSITION_TOP_RATED = 3
        const val POSITION_UPCOMING = 4
    }

    init {
        data.addAll(listOf(
            HomeDataV2(TYPE_TRENDING, PagingData.empty()),
            HomeDataV2(TYPE_CATEGORY, PagingData.empty()),
            HomeDataV2(TYPE_MOVIE, PagingData.empty()),
            HomeDataV2(TYPE_MOVIE, PagingData.empty()),
            HomeDataV2(TYPE_MOVIE, PagingData.empty())
        )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewModelV2<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_TRENDING -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
                TrendingViewHolderV2(context, view, listener, lifecycle)
            }
            TYPE_CATEGORY -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
                TrendingViewHolderV2(context, view, listener, lifecycle)
            }
            TYPE_MOVIE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
                TrendingViewHolderV2(context, view, listener, lifecycle)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: HomeViewModelV2<*>, position: Int) {
        holder.bind(data[position].listItem)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitData(trendingList: PagingData<MovieModel>?) {
        trendingList?.let {
            data[POSITION_TRENDING].listItem = it
            notifyItemChanged(POSITION_TRENDING)
        }
    }

    abstract class HomeViewModelV2<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: PagingData<MovieModel>)
    }
}