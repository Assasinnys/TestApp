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
import com.example.rsschooltask5.api.CatsApi
import com.example.rsschooltask5.model.Cat
import com.example.rsschooltask5.util.BASE_URL
import com.example.rsschooltask5.util.CAT_KEY_BUNDLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DetailFragmentViewModel(app: Application) : AndroidViewModel(app) {
    private val image = MutableLiveData<Drawable>(null)
//    private val category = MutableLiveData<List<Catego>>

    private var cat: Cat? = null
    private val loader = Coil.imageLoader(getApplication<App>())
    private val catsApi = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(CatsApi::class.java)

    fun getImageData(): LiveData<Drawable> = image

    fun saveArgs(args: Bundle?) {
        cat = args?.get(CAT_KEY_BUNDLE) as Cat
        updateUI()
    }

    private fun updateUI() {
        viewModelScope.launch {
            image.value = loadCatPhoto()
        }
    }

    private suspend fun loadCatPhoto(): Drawable? {
        return withContext(Dispatchers.IO) {
            val coilRequest = GetRequest.Builder(getApplication<App>()).data(cat?.imageUrl).build()
            loader.execute(coilRequest).drawable
        }
    }
}