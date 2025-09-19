package com.naulian.composable

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}