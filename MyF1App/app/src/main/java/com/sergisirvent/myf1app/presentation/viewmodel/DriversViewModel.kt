package com.sergisirvent.myf1app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergisirvent.myf1app.data.remote.WikipediaService
import com.sergisirvent.myf1app.domain.usecase.GetDriverDetailUseCase
import com.sergisirvent.myf1app.domain.usecase.GetDriversUseCase
import com.sergisirvent.myf1app.model.F1Driver
import com.sergisirvent.myf1app.model.ResourceState
import com.sergisirvent.myf1app.model.WikipediaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DriversListState = ResourceState<List<F1Driver>>
typealias DriversDetailState = ResourceState<F1Driver>

class DriversViewModel(
    private val driversUseCase: GetDriversUseCase,
    private val driverDetailUseCase: GetDriverDetailUseCase
) : ViewModel() {

    private val driverMutableLiveData = MutableLiveData<DriversListState>()
    private val driverDetailMutableLiveData = MutableLiveData<DriversDetailState>()

    fun getDriverLiveData() : LiveData<DriversListState> {
        return driverMutableLiveData
    }
    fun getDriverDetailLiveData() : LiveData<DriversDetailState> {
        return driverDetailMutableLiveData
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

    fun fetchDriver(f1DriverId : String) {
        driverDetailMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val data = driverDetailUseCase.execute(f1DriverId)

                withContext(Dispatchers.Main){
                    driverDetailMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    driverDetailMutableLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}