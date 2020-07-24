package com.example.rsschooltask5.repository.model

import android.graphics.drawable.Drawable

data class Cat(
    var imageUrl: String = "",
    var image: Drawable? = null,
    var category: String = "",
    var recyclerLoadingFlag: Boolean = false
)
