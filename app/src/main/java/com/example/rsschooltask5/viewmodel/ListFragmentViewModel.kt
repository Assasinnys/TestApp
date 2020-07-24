package com.example.rsschooltask5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rsschooltask5.repository.CatRepository
import com.example.rsschooltask5.repository.model.Cat
import com.example.rsschooltask5.util.RESULT_LIMIT
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
class ListFragmentViewModel @Inject constructor(val catRepository: CatRepository) : ViewModel() {

    private val catList = MutableLiveData<MutableList<Cat>>(mutableListOf())
    private val isLoading = MutableLiveData<Boolean>(true)
    private val nextPageLoading = MutableLiveData<Boolean>(false)

    private var page = 0
    private var paginationCount = 0

    init {
        updateUI()
    }

    fun getCatList(): LiveData<MutableList<Cat>> = catList
    fun isLoading(): LiveData<Boolean> = isLoading
    fun getNextPageLoading(): LiveData<Boolean> = nextPageLoading

    private fun updateUI() {
        viewModelScope.launch {
            showLoading()
            val newCatsPage = catRepository.getCatsList(page)
            catList.value = (catList.value?.plus(newCatsPage) as MutableList<Cat>)
            paginationCount = catRepository.paginationCount
            hideLoading()
        }
    }

    private fun showLoading() {
        isLoading.value = page == 0
        nextPageLoading.value = page != 0
    }

    private fun hideLoading() {
        isLoading.value = false
        nextPageLoading.value = false
    }

    fun notifyRecyclerEnd() {
        if (page.times(RESULT_LIMIT) < paginationCount) {
            page += 1
            updateUI()
        }
    }

    override fun onCleared() {
        viewModelScope.cancel("ViewModel cleared")
    }
}
