package com.sergisirvent.myf1app.data.local

import com.sergisirvent.myf1app.model.Circuit
import com.sergisirvent.myf1app.model.F1Driver

class MemoryCache {

    var f1DriversList: List<F1Driver>? = null
    var circuitsList: List<Circuit>? = null

    fun clearAll() {
        f1DriversList = null
        circuitsList = null
    }

}