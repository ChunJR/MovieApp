package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.R
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_POPULAR
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_TOP_RATED
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_UPCOMING
import chun.project.movieapp.screen.home.adapter.MovieAdapter
import kotlinx.android.synthetic.main.viewholder_movie.view.*

class MovieViewHolder(
    private val context: Context,
    private val view: View,
    private val listener: HomeListener,
    private val lifecycle: Lifecycle
) : HomeAdapter.HomeViewModelV2<List<MovieModel>>(view) {

    private val recyclerView = view.recyclerView
    private val textView = view.textView

    override fun bind(viewType: Int, data: Pair<PagingData<MovieModel>, List<Genres>>) {
        when (viewType) {
            POSITION_POPULAR -> {
                textView.text = context.getString(R.string.txt_popular)
            }
            POSITION_TOP_RATED -> {
                textView.text = context.getString(R.string.txt_top_rated)
            }
            POSITION_UPCOMING -> {
                textView.text = context.getString(R.string.txt_upcoming)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val adapter = MovieAdapter(listener)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        adapter.submitData(lifecycle, data.first)
    }
}