package br.mrenann.ecommerce

import android.app.Application
import br.mrenann.auth.presentation.LoginScreen
import br.mrenann.cart.data.di.cartModule
import br.mrenann.cart.presentation.di.cartModelModule
import br.mrenann.core.di.firebaseModule
import br.mrenann.core.di.networkModule
import br.mrenann.core.di.roomModule
import br.mrenann.favorites.data.di.favoriteModule
import br.mrenann.favorites.presentation.di.myFavoritesModule
import br.mrenann.home.data.di.categoriesModule
import br.mrenann.home.data.di.productsModule
import br.mrenann.home.presentation.di.homeModule
import br.mrenann.navigation.SharedScreen
import br.mrenann.productdetails.data.di.productModule
import br.mrenann.productdetails.presentation.di.detailsModule
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
                    roomModule,
                    firebaseModule,
                    productsModule,
                    categoriesModule,
                    homeModule,
                    favoriteModule,
                    cartModule,
                    cartModelModule,
                    productModule,
                    detailsModule,
                    myFavoritesModule
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        ScreenRegistry {
            register<SharedScreen.LoginScreen> {
                LoginScreen()
            }
        }
    }
}