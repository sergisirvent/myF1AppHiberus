package com.sergisirvent.myf1app.data.circuit

import com.sergisirvent.myf1app.data.circuit.local.CircuitsLocalImpl
import com.sergisirvent.myf1app.data.circuit.remote.CircuitsRemoteImpl
import com.sergisirvent.myf1app.domain.CircuitsRepository
import com.sergisirvent.myf1app.model.Circuit

class CircuitsDataImpl(
    private val circuitsLocalImpl: CircuitsLocalImpl,
    private val circuitsRemoteImpl: CircuitsRemoteImpl
) : CircuitsRepository {

    override suspend fun getCircuits(f1Year: Int, forceRemote: Boolean): List<Circuit> {
        val cachedCircuitsList = circuitsLocalImpl.getF1Circuits()

        if (cachedCircuitsList.isNotEmpty() && !forceRemote) {
            return cachedCircuitsList
        } else {
            val remoteCircuitsList = circuitsRemoteImpl.getCircuits(f1Year)
            saveCircuits(remoteCircuitsList)
            return remoteCircuitsList
        }
    }

    override suspend fun getCircuit(circuitId: Int): Circuit {
        return circuitsRemoteImpl.getCircuit(circuitId)
    }

    override fun saveCircuits(circuitsList: List<Circuit>) {
        circuitsLocalImpl.saveCircuits(circuitsList)
    }

}