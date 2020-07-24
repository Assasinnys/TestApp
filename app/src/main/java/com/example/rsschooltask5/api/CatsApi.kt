package com.example.rsschooltask5.api

import com.example.rsschooltask5.repository.model.CatJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatsApi {

    @GET("v1/images/search")
    suspend fun getCats(
        @Header(value = "x-api-key") headerKey: String,
        @Query(value = "size") size: String,
        @Query(value = "order") order: String,
        @Query(value = "limit") limit: Int,
        @Query(value = "page") page: Int,
        @Query(value = "format") format: String
    ): Response<List<CatJson>>
}
