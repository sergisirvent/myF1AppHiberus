package com.sergisirvent.myf1app.data.driver

import com.sergisirvent.myf1app.data.driver.local.DriversLocalImpl
import com.sergisirvent.myf1app.data.driver.remote.DriversRemoteImpl
import com.sergisirvent.myf1app.domain.DriversRepository
import com.sergisirvent.myf1app.model.F1Driver

class DriversDataImpl(
    private val f1DriversLocalImpl: DriversLocalImpl,
    private val f1DriversRemoteImpl: DriversRemoteImpl
) : DriversRepository {

    override suspend fun getF1Drivers(f1Year: Int, forceRemote: Boolean): List<F1Driver> {
        val cachedDriversList = f1DriversLocalImpl.getF1Drivers()

        if (cachedDriversList.isNotEmpty() && !forceRemote) {
            return cachedDriversList
        } else {
            val remoteF1DriversList = f1DriversRemoteImpl.getF1Drivers(f1Year)
            saveF1Drivers(remoteF1DriversList)
            return remoteF1DriversList
        }
    }

    override suspend fun getF1Driver(f1DriverId: Int): F1Driver {
        return f1DriversRemoteImpl.getF1Driver(f1DriverId)
    }

    override fun saveF1Drivers(f1DriversList: List<F1Driver>) {
        f1DriversLocalImpl.saveF1Drivers(f1DriversList)
    }

}