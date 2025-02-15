package br.mrenann.ecommerce

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            try {
                androidContext(applicationContext)
                modules()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}