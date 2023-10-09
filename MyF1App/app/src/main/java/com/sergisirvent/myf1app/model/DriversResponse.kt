package com.sergisirvent.myf1app.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class DriversResponse(
    @SerializedName("MRData") val f1DriversData: DriversData
)

@Serializable
data class DriversData(
    val DriverTable: DriverTable
)


@Serializable
data class DriverTable(
    val Drivers: List<F1Driver>
)

@Serializable
data class F1Driver(
    val driverId: String,
    val permanentNumber: String,
    val code: String,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String
)