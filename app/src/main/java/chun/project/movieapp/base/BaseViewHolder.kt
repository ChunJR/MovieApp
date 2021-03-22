package chun.project.movieapp.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: List<Any>)
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Any)
}