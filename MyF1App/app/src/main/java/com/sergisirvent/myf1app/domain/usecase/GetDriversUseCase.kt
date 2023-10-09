package com.sergisirvent.myf1app.domain.usecase

import com.sergisirvent.myf1app.domain.DriversRepository
import com.sergisirvent.myf1app.model.F1Driver

class GetDriversUseCase(
    private val f1DriversRepository: DriversRepository
) {
    suspend fun execute(f1Year: Int, forceRemote: Boolean = false) : List<F1Driver> {
        return f1DriversRepository.getF1Drivers(f1Year,forceRemote)
    }
}