package com.example.rsschooltask5.model

import android.graphics.drawable.Drawable

data class CatWithImage(
    var image: Drawable? = null,
    var category: String = "",
    var recyclerLoadingFlag: Boolean = false
)