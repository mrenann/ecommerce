package br.mrenann.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.Navigator

val LocalNavigatorParent = staticCompositionLocalOf<Navigator?> { null }

sealed class SharedScreen : ScreenProvider {
    object LoginScreen : SharedScreen()
    data class ProductDetails(val id: Int) : SharedScreen()
}