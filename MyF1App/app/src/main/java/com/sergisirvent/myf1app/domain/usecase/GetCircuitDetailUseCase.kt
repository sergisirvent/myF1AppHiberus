package com.sergisirvent.myf1app.domain.usecase

import com.sergisirvent.myf1app.domain.CircuitsRepository
import com.sergisirvent.myf1app.model.Circuit

class GetCircuitDetailUseCase(
    private val circuitsRepository: CircuitsRepository
) {
    suspend fun execute(circuitId : String) : Circuit{
        return circuitsRepository.getCircuit(circuitId)
    }
}