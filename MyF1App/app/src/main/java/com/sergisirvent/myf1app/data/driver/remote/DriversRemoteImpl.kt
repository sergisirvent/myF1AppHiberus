package com.sergisirvent.myf1app.data.driver.remote

import com.sergisirvent.myf1app.data.remote.MyF1AppService
import com.sergisirvent.myf1app.model.F1Driver

class DriversRemoteImpl(
    private val f1AppService: MyF1AppService
) {
    suspend fun getF1Drivers(f1Year: Int) : List<F1Driver> {
        return f1AppService.getDrivers(f1Year).f1DriversData.DriverTable.Drivers
    }

    suspend fun getF1Driver(f1DriverId: String) : F1Driver {
        return f1AppService.getDriver(f1DriverId).f1DriversData.DriverTable.Drivers[0]
    }
}