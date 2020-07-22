package com.example.rsschooltask5.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class Cat(
    var imageUrl: String = "",
    var image: Drawable? = null,
    var category: String = "",
    var recyclerLoadingFlag: Boolean = false
)