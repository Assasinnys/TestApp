package com.example.rsschooltask5

import android.app.Application
import com.example.rsschooltask5.di.DaggerAppComponent

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
