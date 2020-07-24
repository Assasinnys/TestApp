package com.example.rsschooltask5.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatJson(
    @Json(name = "url")
    var url: String = "",
    @Json(name = "categories")
    var categories: List<Categories> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class Categories(
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "name")
    var name: String = ""
)
