package com.sergisirvent.myf1app.data.circuit.local

import com.sergisirvent.myf1app.data.local.MemoryCache
import com.sergisirvent.myf1app.model.Circuit

class CircuitsLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getF1Circuits() : List<Circuit> {
        return memoryCache.circuitsList.orEmpty()
    }

    fun saveCircuits(circuits: List<Circuit>){
        memoryCache.circuitsList = circuits
    }
}