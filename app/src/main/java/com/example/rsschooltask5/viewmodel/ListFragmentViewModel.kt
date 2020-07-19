package com.example.rsschooltask5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsschooltask5.model.Cat

class ListFragmentViewModel : ViewModel() {

    private val catList = MutableLiveData<List<Cat>>(emptyList())
    fun getCatList(): LiveData<List<Cat>> = catList
}