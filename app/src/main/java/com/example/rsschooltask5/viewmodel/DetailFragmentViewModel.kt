package com.example.rsschooltask5.viewmodel

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rsschooltask5.repository.CatRepository
import com.example.rsschooltask5.util.CAT_KEY_BUNDLE
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
class DetailFragmentViewModel @Inject constructor(val catRepository: CatRepository) : ViewModel() {

    private val image = MutableLiveData<Drawable>(null)
    private var catImageUrl: String? = null

    fun getImageData(): LiveData<Drawable> = image

    fun saveArgs(args: Bundle?) {
        catImageUrl = args?.getString(CAT_KEY_BUNDLE)
        updateUI()
    }

    private fun updateUI() {
        viewModelScope.launch {
            image.value = catRepository.loadCatPhoto(catImageUrl)
        }
    }

    fun notifySavePhotoClicked() {
        catRepository.saveCatPhoto(image.value?.toBitmap())
    }
}
