package com.example.rsschooltask5.util

import androidx.recyclerview.widget.DiffUtil
import com.example.rsschooltask5.model.Cat

class CatsDiffUtilsCallback(
    private val oldList: List<Cat>,
    private val newList: List<Cat>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].image == newList[newItemPosition].image &&
                oldList[oldItemPosition].category == newList[newItemPosition].category
    }
}