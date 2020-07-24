package com.example.rsschooltask5.viewmodel

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.request.GetRequest
import com.example.rsschooltask5.App
import com.example.rsschooltask5.util.CAT_KEY_BUNDLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val image = MutableLiveData<Drawable>(null)
    private var catImageUrl: String? = null
    private val loader = Coil.imageLoader(getApplication<App>())

    fun getImageData(): LiveData<Drawable> = image

    fun saveArgs(args: Bundle?) {
        catImageUrl = args?.getString(CAT_KEY_BUNDLE)
        updateUI()
    }

    private fun updateUI() {
        viewModelScope.launch {
            image.value = loadCatPhoto()
        }
    }

    private suspend fun loadCatPhoto(): Drawable? {
        return withContext(Dispatchers.IO) {
            val coilRequest = GetRequest.Builder(getApplication<App>()).data(catImageUrl).build()
            loader.execute(coilRequest).drawable
        }
    }
}
