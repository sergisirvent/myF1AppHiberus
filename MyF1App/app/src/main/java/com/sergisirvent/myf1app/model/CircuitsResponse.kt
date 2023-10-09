package com.sergisirvent.myf1app.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CircuitsResponse(
    @SerializedName("MRData") val circuitsData: CircuitsData
)

@Serializable
data class CircuitsData(
    val CircuitTable: CircuitTable
)

@Serializable
data class CircuitTable(
    val Circuits: List<Circuit>
)

@Serializable
data class Circuit(
    val circuitId: String,
    val url: String,
    val circuitName: String,
    val Location: Location
)

@Serializable
data class Location(
    val lat: String,
    val long: String,
    val locality: String,
    val country: String
)
