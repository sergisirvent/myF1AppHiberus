package com.sergisirvent.myf1app.domain

import com.sergisirvent.myf1app.model.Circuit

interface CircuitsRepository {
    suspend fun getCircuits(f1Year: Int,forceRemote: Boolean): List<Circuit>

    suspend fun getCircuit(circuitId: String) : Circuit

    fun saveCircuits(circuitsList: List<Circuit>)
}