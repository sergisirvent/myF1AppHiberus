package com.sergisirvent.myf1app.di

import com.sergisirvent.myf1app.data.circuit.CircuitsDataImpl
import com.sergisirvent.myf1app.data.circuit.local.CircuitsLocalImpl
import com.sergisirvent.myf1app.data.circuit.remote.CircuitsRemoteImpl
import com.sergisirvent.myf1app.data.driver.DriversDataImpl
import com.sergisirvent.myf1app.data.driver.local.DriversLocalImpl
import com.sergisirvent.myf1app.data.driver.remote.DriversRemoteImpl
import com.sergisirvent.myf1app.data.local.MemoryCache
import com.sergisirvent.myf1app.data.remote.ApiClient
import com.sergisirvent.myf1app.data.remote.MyF1AppService
import com.sergisirvent.myf1app.data.remote.WikiApiClient
import com.sergisirvent.myf1app.data.remote.WikipediaService
import com.sergisirvent.myf1app.domain.CircuitsRepository
import com.sergisirvent.myf1app.domain.DriversRepository
import com.sergisirvent.myf1app.domain.usecase.GetCircuitDetailUseCase
import com.sergisirvent.myf1app.domain.usecase.GetCircuitsUseCase
import com.sergisirvent.myf1app.domain.usecase.GetDriverDetailUseCase
import com.sergisirvent.myf1app.domain.usecase.GetDriversUseCase
import com.sergisirvent.myf1app.presentation.viewmodel.CircuitsViewModel
import com.sergisirvent.myf1app.presentation.viewmodel.DriversViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    single { MemoryCache() }
    single<MyF1AppService> {ApiClient.retrofit.create(MyF1AppService::class.java)}
    single<WikipediaService> {WikiApiClient.retrofit.create(WikipediaService::class.java)}
}

val driversModule = module {
    factory { DriversLocalImpl(get()) }
    factory { DriversRemoteImpl(get()) }
    factory <DriversRepository> { DriversDataImpl(get(), get()) }
    factory { GetDriversUseCase(get()) }
    factory { GetDriverDetailUseCase(get()) }

    viewModel { DriversViewModel(get(), get())}


}
val circuitsModule = module {

    factory { CircuitsLocalImpl(get()) }
    factory { CircuitsRemoteImpl(get()) }
    factory <CircuitsRepository> { CircuitsDataImpl(get(), get()) }
    factory { GetCircuitsUseCase(get()) }
    factory { GetCircuitDetailUseCase(get()) }


    viewModel { CircuitsViewModel(get(), get())}
}