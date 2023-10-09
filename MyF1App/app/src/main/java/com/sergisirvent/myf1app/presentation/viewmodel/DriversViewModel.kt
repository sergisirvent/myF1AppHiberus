package com.sergisirvent.myf1app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergisirvent.myf1app.data.remote.WikipediaService
import com.sergisirvent.myf1app.domain.usecase.GetDriversUseCase
import com.sergisirvent.myf1app.model.F1Driver
import com.sergisirvent.myf1app.model.ResourceState
import com.sergisirvent.myf1app.model.WikipediaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DriversListState = ResourceState<List<F1Driver>>

class DriversViewModel(
    private val driversUseCase: GetDriversUseCase
) : ViewModel() {

    private val driverMutableLiveData = MutableLiveData<DriversListState>()

    fun getDriverLiveData() : LiveData<DriversListState> {
        return driverMutableLiveData
    }

    fun fetchDrivers(f1Year : Int) {
        driverMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val data = driversUseCase.execute(f1Year = f1Year,forceRemote = false)

                withContext(Dispatchers.Main){
                    driverMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    driverMutableLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}