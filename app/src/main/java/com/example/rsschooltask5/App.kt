package com.example.rsschooltask5

import android.app.Application
import com.example.rsschooltask5.repository.CatRepository

class App : Application() {
    val catRepository: CatRepository by lazy { CatRepository(applicationContext) }
}
