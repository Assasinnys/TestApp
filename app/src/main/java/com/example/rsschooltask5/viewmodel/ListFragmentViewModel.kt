package com.example.rsschooltask5.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.request.GetRequest
import coil.transform.CircleCropTransformation
import com.example.rsschooltask5.App
import com.example.rsschooltask5.api.CatsApi
import com.example.rsschooltask5.model.Cat
import com.example.rsschooltask5.model.CatJson
/* ktlint-disable no-wildcard-imports */
import com.example.rsschooltask5.util.*
/* ktlint-enable no-wildcard-imports */
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val catList = MutableLiveData<MutableList<Cat>>(mutableListOf())
    private val isLoading = MutableLiveData<Boolean>(true)
    private val nextPageLoading = MutableLiveData<Boolean>(false)

    private val loader = Coil.imageLoader(getApplication<App>())
    private var page = 0
    private var paginationCount = 0
    private val catsApi = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(CatsApi::class.java)

    init {
        updateUI()
    }

    fun getCatList(): LiveData<MutableList<Cat>> = catList
    fun isLoading(): LiveData<Boolean> = isLoading
    fun getNextPageLoading(): LiveData<Boolean> = nextPageLoading

    private fun updateUI() {
        viewModelScope.launch {
            showLoading()
            val response = catsApi.getCats(
                API_KEY, FULL_SIZE, CATS_ORDER, RESULT_LIMIT, page, RESPONSE_FORMAT
            )
            if (response.isSuccessful) {
                paginationCount = response.headers()[PAGINATION_HEADER]?.toInt() ?: 0
                Log.d("ASD", "parg count = $paginationCount")
                val catsWithImages = requestCatImages(response.body())
                catList.value = (catList.value?.plus(catsWithImages) as MutableList<Cat>)
            } else {
                Log.e("ASD", "Error due loading")
            }
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

    private suspend fun requestCatImages(catJsonList: List<CatJson>?): List<Cat> {
        val catsWithImage = mutableListOf<Cat>()
        if (catJsonList.isNullOrEmpty()) return catsWithImage

        catJsonList.forEach { cat ->
            val request = GetRequest.Builder(getApplication<App>()).data(cat.url)
                .transformations(CircleCropTransformation()).build()
            val image = loader.execute(request).drawable
            catsWithImage.add(
                Cat(
                    image = image,
                    category = cat.categories.joinToString(", ") { it.name },
                    imageUrl = cat.url
                )
            )
        }
        return catsWithImage
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

    companion object {
        const val PAGINATION_HEADER = "Pagination-Count"
    }
}
