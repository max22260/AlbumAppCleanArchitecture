package com.nagy.assessment

import android.app.Application
import com.nagy.logging.Logger
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AssessmentApp : Application() {


    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Logger.init()
    }
}