package com.example.rsschooltask5.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rsschooltask5.model.Cat

class CatListAdapter(private var cats: MutableList<Cat> = mutableListOf()) :
    RecyclerView.Adapter<CatListAdapter.CatViewHolder>() {

    fun setCats(newCats: List<Cat>) {
        // TODO DiffUtils
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = cats.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class CatViewHolder(private val root: View) : RecyclerView.ViewHolder(root)
}