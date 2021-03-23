package chun.project.movieapp.screen.home.viewholder

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.R
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
import chun.project.movieapp.screen.home.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.viewholder_trending.view.*

class TrendingViewHolder(
    private val context: Context,
    private val view: View,
    private val listener: HomeListener,
    private val lifecycle: Lifecycle
) : HomeAdapter.HomeViewModelV2<List<MovieModel>>(view) {

    private val recyclerView = view.recyclerView

    private var adapter: TrendingAdapter? = null

    override fun bind(viewType: Int, data: Pair<PagingData<MovieModel>, List<Genres>>) {
        adapter = TrendingAdapter(listener)
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
                androidx.appcompat.app.AlertDialog.Builder(view.context)
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