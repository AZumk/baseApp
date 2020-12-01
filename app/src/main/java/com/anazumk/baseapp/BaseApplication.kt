package com.anazumk.baseapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.anazumk.baseapp.dependencyinjection.baseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startDependencyInjection()
    }

    private fun startDependencyInjection(){
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(
                baseModule
            ))
        }
    }
}