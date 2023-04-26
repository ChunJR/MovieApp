package chun.project.movieapp.screen.home.viewholder

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import chun.project.movieapp.databinding.ViewholderCategoryBinding
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.adapter.CategoryAdapter
import chun.project.movieapp.screen.home.adapter.HomeAdapter

class CategoryViewHolder(
    private val context: Context,
    private val binding: ViewholderCategoryBinding,
    private val listener: HomeListener,
    private val lifecycle: Lifecycle
) : HomeAdapter.HomeViewModelV2<List<MovieModel>>(binding.root) {

    private val recyclerView = binding.recyclerView
    private var adapter: CategoryAdapter? = null

    override fun bind(viewType: Int, data: Pair<PagingData<MovieModel>, List<Genres>>) {
        adapter?.let {
            it.updateDataList(data.second)
            recyclerView.scrollToPosition(0)
        } ?: run {
            adapter = CategoryAdapter(data.second, listener)
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }
}
