package com.sergisirvent.myf1app.data.remote

import android.provider.Settings.Global.getString
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.model.Circuit
import com.sergisirvent.myf1app.model.CircuitsResponse
import com.sergisirvent.myf1app.model.DriversResponse
import com.sergisirvent.myf1app.model.F1Driver
import com.sergisirvent.myf1app.model.WikipediaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyF1AppService {

    @GET("{year}/drivers.json")
    suspend fun getDrivers(@Path("year") year: Int): DriversResponse

    @GET("drivers/{f1DriverId}.json")
    suspend fun getDriver(@Path("f1DriverId") f1DriverId: Int): F1Driver

    @GET("{year}/circuits.json")
    suspend fun getCircuits(@Path("year") year: Int): CircuitsResponse

    @GET("circuits/{circuitId}.json")
    suspend fun getCircuit(@Path("circuitId") circuitId: Int): Circuit


}