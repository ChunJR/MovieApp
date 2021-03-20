package chun.project.movieapp.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.model.TrendingModel

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: List<*>)
}