package com.news

import android.app.Application
import com.news.di.modules.appModules
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(appModules)
        }
    }
}