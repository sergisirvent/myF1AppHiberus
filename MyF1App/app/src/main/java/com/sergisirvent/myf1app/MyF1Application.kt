package com.sergisirvent.myf1app

import android.app.Application
import com.sergisirvent.myf1app.di.baseModule
import com.sergisirvent.myf1app.di.circuitsModule
import com.sergisirvent.myf1app.di.driversModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyF1Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyF1Application)
            modules(listOf(baseModule, driversModule, circuitsModule)).allowOverride(true)
        }
    }

}