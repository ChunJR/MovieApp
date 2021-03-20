package chun.project.movieapp.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.base.BaseViewHolder
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.model.TrendingResponseModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import java.util.ArrayList

class HomeAdapter(private val listener: HomeListener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val data: MutableList<*>

    companion object {
        const val TYPE_TRENDING = 0
        private val TYPE_TRAILER = 1
        private val TYPE_REVIEW = 2
    }

    init {
        data = arrayListOf()
        data.add(TYPE_TRENDING, arrayListOf<TrendingModel>())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_TRENDING -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
                TrendingViewHolder(view, listener, data[TYPE_TRENDING])
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
        val viewType = data[position].viewType
        val item = data[position].data
        when(viewType){
            TYPE_TRENDING -> holder.bind(item)
//            is TrailerViewHolder -> holder.bind(element as Trailer)
//            is ReviewViewHolder -> holder.bind(element as Review)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun getItemCount(): Int {
        return data.size
    }
}