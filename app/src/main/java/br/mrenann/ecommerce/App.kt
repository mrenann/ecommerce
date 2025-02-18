package br.mrenann.ecommerce

import android.app.Application
import br.mrenann.core.di.networkModule
import cafe.adriel.voyager.core.registry.ScreenRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            try {
                androidContext(applicationContext)
                modules(
                    networkModule
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        ScreenRegistry {
        }
    }
}