package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.R
import chun.project.movieapp.databinding.ViewholderMovieBinding
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_POPULAR
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_TOP_RATED
import chun.project.movieapp.screen.home.adapter.HomeAdapter.Companion.POSITION_UPCOMING
import chun.project.movieapp.screen.home.adapter.MovieAdapter

class MovieViewHolder(
    private val context: Context,
    private val binding: ViewholderMovieBinding,
    private val listener: HomeListener,
    private val lifecycle: Lifecycle
) : HomeAdapter.HomeViewModelV2<List<MovieModel>>(binding.root) {

    private val recyclerView = binding.recyclerView
    private val textView = binding.textView

    private var adapter: MovieAdapter? = null

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

        adapter = MovieAdapter(listener)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        adapter?.submitData(lifecycle, data.first)

        adapter?.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                AlertDialog.Builder(context)
                    .setTitle(R.string.txt_error)
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton(R.string.txt_cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.txt_retry) { _, _ ->
                        adapter?.retry()
                    }
                    .show()
            }
        }
    }
}