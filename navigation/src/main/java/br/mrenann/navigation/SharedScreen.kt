package br.mrenann.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen: ScreenProvider {
    object WelcomeScreen: SharedScreen()
}