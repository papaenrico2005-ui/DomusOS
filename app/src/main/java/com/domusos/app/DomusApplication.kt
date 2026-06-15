package com.domusos.app

import android.app.Application
import com.domusos.app.di.AppContainer

class DomusApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}