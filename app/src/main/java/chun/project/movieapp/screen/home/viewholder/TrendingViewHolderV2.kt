package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.HomeAdapter
import chun.project.movieapp.screen.home.adapter.HomeAdapterV2
import chun.project.movieapp.screen.home.adapter.LoadingStateAdapter
import chun.project.movieapp.screen.home.adapter.TrendingAdapterV2
import chun.project.movieapp.util.checkItemsAre
import kotlinx.android.synthetic.main.item_trending.view.*

class TrendingViewHolderV2(private val context: Context,
                           private val view: View,
                           private val listener: HomeListener,
                           private val lifecycle: Lifecycle
) : HomeAdapterV2.HomeViewModelV2<List<MovieModel>>(view) {

    private lateinit var trendingList: List<MovieModel>

    private val recyclerView = view.recyclerView
    private val rootView = view.rootView

    override fun bind(data: PagingData<MovieModel>) {
            rootView.visibility = View.VISIBLE

            val adapter = TrendingAdapterV2(context, listener)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter()
            )
            adapter.submitData(lifecycle, data)
//            adapter.addLoadStateListener { loadState ->
//                val errorState = loadState.source.append as? LoadState.Error
//                    ?: loadState.source.prepend as? LoadState.Error
//                    ?: loadState.append as? LoadState.Error
//                    ?: loadState.prepend as? LoadState.Error
//
//                errorState?.let {
//                    AlertDialog.Builder(view.context)
//                        .setTitle(R.string.error)
//                        .setMessage(it.error.localizedMessage)
//                        .setNegativeButton(R.string.cancel) { dialog, _ ->
//                            dialog.dismiss()
//                        }
//                        .setPositiveButton(R.string.retry) { _, _ ->
//                            adapter.retry()
//                        }
//                        .show()
//                }
//            }
    }
}