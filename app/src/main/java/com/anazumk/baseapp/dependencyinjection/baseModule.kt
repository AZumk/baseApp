package com.anazumk.baseapp.dependencyinjection

import com.anazumk.baseapp.main.MainViewModel
import org.koin.dsl.module

val baseModule = module {
    single { MainViewModel() }
}