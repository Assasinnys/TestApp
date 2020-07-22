package com.example.rsschooltask5.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rsschooltask5.R
import com.example.rsschooltask5.model.CatWithImage
import com.example.rsschooltask5.util.CatsDiffUtilsCallback
import kotlinx.android.synthetic.main.item_cat.view.*
import kotlinx.android.synthetic.main.item_progress.view.*

class CatListAdapter(
    private var cats: MutableList<CatWithImage> = mutableListOf(),
    private val onCatPhotoClickListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * For progress bar at the end correct work, LiveData's list and adapter's list must be separated from each other
     */
    fun setCats(newCats: MutableList<CatWithImage>) {
        val diffCallback = CatsDiffUtilsCallback(cats, newCats)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        cats.apply {
            clear()
            addAll(newCats)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return if (cats[position].recyclerLoadingFlag) LOADING
        else NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)/*.inflate(R.layout.item_cat, parent, false)*/

        return when (viewType) {
            NORMAL -> CatViewHolder(
                inflater.inflate(R.layout.item_cat, parent, false),
                onCatPhotoClickListener
            )
            LOADING -> LoadingViewHolder(inflater.inflate(R.layout.item_progress, parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount() = cats.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CatViewHolder -> holder.bind(cats[position])
            is LoadingViewHolder -> holder.root.pb_loading_recycler.show()
        }
    }

    fun addLoadingView() {
        if (cats.isEmpty() || cats[cats.lastIndex].recyclerLoadingFlag) return

        cats.add(
            CatWithImage(
                recyclerLoadingFlag = true
            )
        )
        notifyItemInserted(cats.lastIndex)
    }

    fun removeLoadingView() {
        if (cats.isEmpty() || !cats[cats.lastIndex].recyclerLoadingFlag) return

        cats.removeAt(cats.lastIndex)
        notifyItemRemoved(cats.size)
    }

    class CatViewHolder(private val root: View, private val onCatPhotoClickListener: () -> Unit) :
        RecyclerView.ViewHolder(root) {
        init {
            root.iv_cat_photo.setOnClickListener { onCatPhotoClickListener() }
        }

        fun bind(cat: CatWithImage) {
            root.iv_cat_photo.setImageDrawable(cat.image)
            root.tv_cat_category.text = cat.category
        }
    }

    class LoadingViewHolder(val root: View) : RecyclerView.ViewHolder(root)

    companion object {
        const val NORMAL = 0
        const val LOADING = 1
    }
}