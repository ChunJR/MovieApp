package chun.project.movieapp.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.model.checkItemsAre
import chun.project.movieapp.screen.home.`interface`.HomeListener

class HomeAdapter(private val listener: HomeListener, private val data: MutableList<Any>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        const val TYPE_TRENDING = 0
        private val TYPE_TRAILER = 1
        private val TYPE_REVIEW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_TRENDING -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)

                val trendingList = data.checkItemsAre<TrendingModel>() ?: return arrayListOf<>()
                TrendingViewHolder(view, listener, trendingList)
            }
//            TYPE_TRAILER -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_list_content, parent, false)
//                TrailerViewHolder(view, listener, data)
//            }
//            TYPE_REVIEW -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.review_list_content, parent, false)
//                ReviewViewHolder(view, listener, data)
//            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (position) {
            TYPE_TRENDING -> holder.bind(data[position] as List<*>)
//            is TrailerViewHolder -> holder.bind(element as Trailer)
//            is ReviewViewHolder -> holder.bind(element as Review)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return data.size
    }
}