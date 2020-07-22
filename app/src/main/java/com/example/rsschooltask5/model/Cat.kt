package com.example.rsschooltask5.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Cat(
    var imageUrl: String = "",
    var image: @RawValue Drawable? = null,
    var category: String = "",
    var recyclerLoadingFlag: Boolean = false
): Parcelable