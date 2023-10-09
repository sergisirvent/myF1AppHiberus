package com.sergisirvent.myf1app.domain.usecase

import com.sergisirvent.myf1app.domain.CircuitsRepository
import com.sergisirvent.myf1app.model.Circuit

class GetCircuitsUseCase(
    private val circuitsRepository: CircuitsRepository
) {
    suspend fun execute(year: Int, forceRemote: Boolean = false) : List<Circuit> {
        return circuitsRepository.getCircuits(year,forceRemote)
    }
}