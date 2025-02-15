package br.mrenann.main.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import br.mrenann.favorites.presentation.FavoritesTab
import br.mrenann.home.presentation.HomeTab
import br.mrenann.main.presentation.components.MainContent
import br.mrenann.profile.presentation.ProfileTab
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

val LocalNavigatorParent = staticCompositionLocalOf<Navigator?> { null }

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigatorParent = LocalNavigator.current
        val tabs = listOf<Tab>(HomeTab(), FavoritesTab(), ProfileTab())
        TabNavigator(
            tab = HomeTab(),
            tabDisposable = {
                TabDisposable(
                    navigator = it, tabs = tabs
                )
            }

        ) {
            CompositionLocalProvider(LocalNavigatorParent provides navigatorParent) {
                MainContent(tabs)
            }
        }
    }

}