package com.sergisirvent.myf1app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://ergast.com/api/f1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}