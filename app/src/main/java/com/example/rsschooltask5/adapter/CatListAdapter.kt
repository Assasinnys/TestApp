package com.example.rsschooltask5.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.load
import coil.request.GetRequest
import coil.transform.CircleCropTransformation
import com.example.rsschooltask5.R
import com.example.rsschooltask5.model.CatJson
import com.example.rsschooltask5.model.CatWithImage
import com.example.rsschooltask5.util.CatsDiffUtilsCallback
import kotlinx.android.synthetic.main.item_cat.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatListAdapter(
    private var cats: List<CatWithImage> = mutableListOf(),
    private val nextPageListener: () -> Unit
) : RecyclerView.Adapter<CatListAdapter.CatViewHolder>() {

    fun setCats(newCats: List<CatWithImage>) {
        val diffCallback = CatsDiffUtilsCallback(cats, newCats)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        cats = newCats
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(root)
    }

    override fun getItemCount() = cats.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.root.iv_cat_photo.setImageDrawable(cats[position].image)
        holder.root.tv_cat_category.text = cats[position].category

        if (position.plus(3) == cats.size) {
            nextPageListener.invoke()
        }
    }

    class CatViewHolder(val root: View) : RecyclerView.ViewHolder(root)
}