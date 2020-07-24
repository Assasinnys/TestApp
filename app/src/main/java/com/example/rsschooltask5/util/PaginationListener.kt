package com.example.rsschooltask5.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationListener(
    private val nextPageListener: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var isLoading = false
    private val visibleLimit = 4

    fun loadingFinished() {
        isLoading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        if (!isLoading && totalItemCount <= lastVisibleItem.plus(visibleLimit)) {
            nextPageListener()
            isLoading = true
        }
    }
}
