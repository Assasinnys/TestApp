package com.example.rsschooltask5.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.IS_PENDING
import android.util.Log
import androidx.core.content.contentValuesOf
import coil.Coil
import coil.request.GetRequest
import coil.transform.CircleCropTransformation
import com.example.rsschooltask5.api.CatsApi
import com.example.rsschooltask5.repository.model.Cat
import com.example.rsschooltask5.repository.model.CatJson
import com.example.rsschooltask5.util.* // ktlint-disable no-wildcard-imports
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CatRepository(private val appContext: Context) {

    private val imageLoader = Coil.imageLoader(appContext)
    private val catsApi = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(CatsApi::class.java)

    var paginationCount: Int = 0
        private set

    suspend fun getCatsList(page: Int): List<Cat> {
        val response = catsApi.getCats(
            API_KEY, FULL_SIZE, CATS_ORDER, RESULT_LIMIT, page, RESPONSE_FORMAT
        )

        return if (response.isSuccessful) {
            paginationCount =
                response.headers()[PAGINATION_HEADER]?.toInt() ?: 0
            requestCatImages(response.body())
        } else {
            Log.e("ASD", "Error due loading")
            emptyList()
        }
    }

    private suspend fun requestCatImages(catJsonList: List<CatJson>?): List<Cat> {
        val catsWithImage = mutableListOf<Cat>()
        if (catJsonList.isNullOrEmpty()) return catsWithImage

        catJsonList.forEach { cat ->
            val image = loadCatPhoto(cat.url, true)
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

    suspend fun loadCatPhoto(url: String?, isCircleImage: Boolean = false): Drawable? {
        val request = if (isCircleImage) {
            GetRequest.Builder(appContext).data(url)
                .transformations(CircleCropTransformation()).build()
        } else {
            GetRequest.Builder(appContext).data(url).build()
        }
        return imageLoader.execute(request).drawable
    }

    fun saveCatPhoto(photo: Bitmap?) {
        val contentResolver = appContext.contentResolver
        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val cv = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "My_Awesome_Cat.png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(IS_PENDING, 1)
            }
        }

        val uri = contentResolver.insert(imageCollection, cv)
        if (uri != null) {
            contentResolver.openOutputStream(uri).use {
                photo?.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentResolver.update(
                    uri,
                    contentValuesOf(IS_PENDING to 0),
                    null,
                    null
                )
            }
        }
    }

    companion object {
        const val PAGINATION_HEADER = "Pagination-Count"
    }
}
