package com.sergisirvent.myf1app.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergisirvent.myf1app.domain.usecase.GetCircuitDetailUseCase
import com.sergisirvent.myf1app.domain.usecase.GetCircuitsUseCase
import com.sergisirvent.myf1app.model.Circuit
import com.sergisirvent.myf1app.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias CircuitsListState = ResourceState<List<Circuit>>
typealias CircuitsDetailState = ResourceState<Circuit>

class CircuitsViewModel(
    private val circuitsUseCase: GetCircuitsUseCase,
    private val circuitDetailUseCase: GetCircuitDetailUseCase
) : ViewModel(){

    private val circuitMutableLiveData = MutableLiveData<CircuitsListState>()
    private val circuitDetailMutableLiveData = MutableLiveData<CircuitsDetailState>()

    fun getCircuitLiveData() : LiveData<CircuitsListState> {
        return circuitMutableLiveData
    }
    fun getCircuitDetailLiveData() : LiveData<CircuitsDetailState> {
        return circuitDetailMutableLiveData
    }

    fun fetchCircuits(f1Year : Int) {
        circuitMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val data = circuitsUseCase.execute(year = f1Year,forceRemote = false)

                withContext(Dispatchers.Main){
                    circuitMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    circuitMutableLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
    fun fetchCircuit(circuitId : String) {
        circuitDetailMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val data = circuitDetailUseCase.execute(circuitId)

                withContext(Dispatchers.Main){
                    circuitDetailMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    circuitDetailMutableLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}