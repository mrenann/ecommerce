package br.mrenann.ecommerce

import android.app.Application
import br.mrenann.core.di.networkModule
import br.mrenann.home.data.di.productsModule
import br.mrenann.home.presentation.di.homeModule
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
                    networkModule,
                    productsModule,
                    homeModule
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        ScreenRegistry {
        }
    }
}