package chun.project.movieapp.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.HomeData
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.viewholder.TrendingViewHolder

class HomeAdapter(private val listener: HomeListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val data: MutableList<HomeData> = arrayListOf()

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
            HomeData(TYPE_TRENDING, arrayListOf()),
            HomeData(TYPE_CATEGORY, arrayListOf()),
            HomeData(TYPE_MOVIE, arrayListOf()),
            HomeData(TYPE_MOVIE, arrayListOf()),
            HomeData(TYPE_MOVIE, arrayListOf())
        ))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_TRENDING -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
                TrendingViewHolder(context, view, listener)
            }
            TYPE_CATEGORY -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
                TrendingViewHolder(context, view, listener)
            }
            TYPE_MOVIE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
                TrendingViewHolder(context, view, listener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(data[position].listItem)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateTrendingList(trendingList: List<TrendingModel>?) {
        trendingList?.let {
            data[POSITION_TRENDING].listItem = it
            notifyItemChanged(POSITION_TRENDING)
        }
    }
}