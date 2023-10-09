package com.sergisirvent.myf1app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WikiApiClient {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=original&titles=")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}