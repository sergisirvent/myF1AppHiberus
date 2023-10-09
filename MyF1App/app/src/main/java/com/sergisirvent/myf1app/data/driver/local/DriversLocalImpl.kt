package com.sergisirvent.myf1app.data.driver.local

import com.sergisirvent.myf1app.data.local.MemoryCache
import com.sergisirvent.myf1app.model.F1Driver


class DriversLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getF1Drivers() : List<F1Driver> {
        return memoryCache.f1DriversList.orEmpty()
    }

    fun saveF1Drivers(f1Drivers: List<F1Driver>){
        memoryCache.f1DriversList = f1Drivers
    }
}