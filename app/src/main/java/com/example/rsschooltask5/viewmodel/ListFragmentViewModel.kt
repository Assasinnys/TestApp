package com.example.rsschooltask5.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import coil.Coil
import coil.request.GetRequest
import coil.transform.CircleCropTransformation
import com.example.rsschooltask5.App
import com.example.rsschooltask5.api.CatsApi
import com.example.rsschooltask5.model.CatJson
import com.example.rsschooltask5.model.CatWithImage
import com.example.rsschooltask5.util.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val catList = MutableLiveData<List<CatWithImage>>(mutableListOf())

    private val loader = Coil.imageLoader(getApplication<App>())
    private var page = 0
    private val catsApi = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(CatsApi::class.java)

    init {
        updateUI()
    }

    fun getCatList(): LiveData<List<CatWithImage>> = catList

    private fun updateUI() {
        viewModelScope.launch {
            val response = catsApi.getCats(
                API_KEY, SMALL_SIZE, "ASC", RESULT_LIMIT, page, RESPONSE_FORMAT
            )
            if (response.isSuccessful) {
                catList.value = catList.value?.plus(requestCatsImages(response.body()))
            } else {
                // TODO show error
            }
        }
    }

    private suspend fun requestCatsImages(catJsonList: List<CatJson>?): List<CatWithImage> {
        val catsWithImage = mutableListOf<CatWithImage>()
        if (catJsonList.isNullOrEmpty()) return catsWithImage

        catJsonList.forEach { cat ->
            val request = GetRequest.Builder(getApplication<App>()).data(cat.url)
                .transformations(CircleCropTransformation()).build()
            val image = loader.execute(request).drawable
            catsWithImage.add(
                CatWithImage(
                    image = image,
                    category = cat.categories.joinToString(", ") { it.name }
                )
            )
        }
        return catsWithImage
    }

    fun requestNextPage() {
        page += 1
        updateUI()
    }
}