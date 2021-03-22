package chun.project.movieapp.screen.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.R
import chun.project.movieapp.databinding.LoadingItemBinding

class LoadingStateViewHolder(
    private val binding: LoadingItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup): LoadingStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_item, parent, false)

            val binding = LoadingItemBinding.bind(view)

            return LoadingStateViewHolder(
                binding
            )
        }
    }
}