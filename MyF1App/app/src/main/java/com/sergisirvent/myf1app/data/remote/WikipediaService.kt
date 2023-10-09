package com.sergisirvent.myf1app.data.remote

import com.sergisirvent.myf1app.model.WikipediaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaService {
    @GET("w/api.php")
    suspend fun getImageInfo(
        @Query("action") action: String = "query",
        @Query("format") format: String = "json",
        @Query("prop") prop: String = "pageimages",
        @Query("pithumbsize") thumbSize: Int = 500,
        @Query("titles") pageTitle: String
    ): WikipediaResponse
}