package com.example.rsschooltask5.util

import androidx.recyclerview.widget.DiffUtil
import com.example.rsschooltask5.model.CatJson
import com.example.rsschooltask5.model.CatWithImage

class CatsDiffUtilsCallback(
    private val oldList: List<CatWithImage>,
    private val newList: List<CatWithImage>
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