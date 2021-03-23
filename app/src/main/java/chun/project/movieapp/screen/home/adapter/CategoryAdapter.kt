package chun.project.movieapp.screen.home.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import chun.project.movieapp.databinding.ItemCategoryBinding
import chun.project.movieapp.model.Genres
import chun.project.movieapp.screen.home.`interface`.HomeListener
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.CATEGORY_HEIGHT
import chun.project.movieapp.screen.home.ui.HomeFragment.Companion.CATEGORY_WIDTH
import chun.project.movieapp.util.px
import java.util.*


class CategoryAdapter(
    private var categoryList: List<Genres>,
    private val listener: HomeListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]

        val layoutParams = FrameLayout.LayoutParams(
            CATEGORY_WIDTH.px,
            CATEGORY_HEIGHT.px
        )

        holder.binding.textView.layoutParams = layoutParams
        holder.binding.textView.text = category.name

        holder.binding.cardView.setCardBackgroundColor(getRandomColor())
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun updateDataList(categoryList: List<Genres>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    private fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}