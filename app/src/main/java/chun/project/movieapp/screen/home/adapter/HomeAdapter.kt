package chun.project.movieapp.screen.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.HomeDataV2
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.viewholder.CategoryViewHolder
import chun.project.movieapp.screen.home.viewholder.MovieViewHolder
import chun.project.movieapp.screen.home.viewholder.TrendingViewHolder

class HomeAdapter(private val lifecycle: Lifecycle, private val listener: HomeListener) :
    RecyclerView.Adapter<HomeAdapter.HomeViewModelV2<*>>() {

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
        val emptyObject = Pair<PagingData<MovieModel>, List<Genres>>(
            PagingData.empty(),
            arrayListOf()
        )

        data.addAll(listOf(
            HomeDataV2(TYPE_TRENDING, emptyObject),
            HomeDataV2(TYPE_CATEGORY, emptyObject),
            HomeDataV2(TYPE_MOVIE, emptyObject),
            HomeDataV2(TYPE_MOVIE, emptyObject),
            HomeDataV2(TYPE_MOVIE, emptyObject)
        )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewModelV2<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_TRENDING -> {
                val view = LayoutInflater.from(context).inflate(R.layout.viewholder_trending, parent, false)
                TrendingViewHolder(context, view, listener, lifecycle)
            }
            TYPE_CATEGORY -> {
                val view = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false)
                CategoryViewHolder(context, view, listener, lifecycle)
            }
            TYPE_MOVIE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.viewholder_movie, parent, false)
                MovieViewHolder(context, view, listener, lifecycle)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: HomeViewModelV2<*>, position: Int) {
        holder.bind(position, data[position].listItem)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitData(position: Int, movieList: PagingData<MovieModel>?) {
        movieList?.let {
            val pagingObject = Pair<PagingData<MovieModel>, List<Genres>>(
                it,
                arrayListOf()
            )
            data[position].listItem = pagingObject
            notifyItemChanged(position)
        }
    }
    fun updateCategories(position: Int, genresList: List<Genres>?) {
        genresList?.let {
            val listObject = Pair<PagingData<MovieModel>, List<Genres>>(
                PagingData.empty(),
                it
            )
            data[position].listItem = listObject
            notifyItemChanged(position)
        }
    }

    fun onRefreshData() {
        data.forEach {
            it.isRefresh = true
        }
        notifyDataSetChanged()
    }

    abstract class HomeViewModelV2<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(viewType: Int, data: Pair<PagingData<MovieModel>, List<Genres>>)
    }
}