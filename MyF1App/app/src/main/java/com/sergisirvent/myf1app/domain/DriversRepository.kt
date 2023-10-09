package com.sergisirvent.myf1app.domain

import com.sergisirvent.myf1app.model.F1Driver

interface DriversRepository {

    suspend fun getF1Drivers(f1Year: Int,forceRemote: Boolean): List<F1Driver>

    suspend fun getF1Driver(f1DriverId: String) : F1Driver

    fun saveF1Drivers(f1DriversList: List<F1Driver>)

}