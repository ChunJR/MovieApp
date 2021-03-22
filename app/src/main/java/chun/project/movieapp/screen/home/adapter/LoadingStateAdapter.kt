package chun.project.movieapp.screen.home.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import chun.project.movieapp.screen.home.viewholder.LoadingStateViewHolder

class LoadingStateAdapter: LoadStateAdapter<LoadingStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder {
        return LoadingStateViewHolder.create(parent)
    }
}