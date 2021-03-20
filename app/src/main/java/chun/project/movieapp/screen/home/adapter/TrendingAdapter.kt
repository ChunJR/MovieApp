package chun.project.movieapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.databinding.ItemTrendingMovieBinding
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.screen.home.`interface`.HomeListener
import com.bumptech.glide.Glide

class TrendingAdapter(private val context: Context,
                      private val trendingList: List<TrendingModel>,
                      private val listener: HomeListener,) :
    RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingAdapter.ViewHolder {
        val binding = ItemTrendingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    inner class ViewHolder(val binding: ItemTrendingMovieBinding) :RecyclerView.ViewHolder(binding.root)
}