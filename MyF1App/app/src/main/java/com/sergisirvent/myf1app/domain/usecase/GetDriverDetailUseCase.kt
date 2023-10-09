package com.sergisirvent.myf1app.domain.usecase

import com.sergisirvent.myf1app.domain.DriversRepository
import com.sergisirvent.myf1app.model.F1Driver

class GetDriverDetailUseCase(
    private val driversRepository: DriversRepository
) {
    suspend fun execute(driverId : String) : F1Driver {
        return driversRepository.getF1Driver(driverId)
    }
}