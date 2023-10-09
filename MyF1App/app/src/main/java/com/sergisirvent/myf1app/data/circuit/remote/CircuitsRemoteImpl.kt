package com.sergisirvent.myf1app.data.circuit.remote

import com.sergisirvent.myf1app.data.remote.MyF1AppService
import com.sergisirvent.myf1app.model.Circuit

class CircuitsRemoteImpl(
    private val myF1AppService: MyF1AppService
) {
    suspend fun getCircuits(f1Year: Int) : List<Circuit> {
        return myF1AppService.getCircuits(f1Year).circuitsData.CircuitTable.Circuits
    }

    suspend fun getCircuit(circuitId: String) : Circuit {
        return myF1AppService.getCircuit(circuitId).circuitsData.CircuitTable.Circuits[0]
    }
}