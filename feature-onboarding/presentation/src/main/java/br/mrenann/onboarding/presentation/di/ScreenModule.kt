package br.mrenann.onboarding.presentation.di

import br.mrenann.navigation.SharedScreen
import br.mrenann.onboarding.presentation.WelcomeScreen
import cafe.adriel.voyager.core.registry.screenModule

val onboardingScreenModule = screenModule {
    register<SharedScreen.WelcomeScreen> {
        WelcomeScreen()
    }

}