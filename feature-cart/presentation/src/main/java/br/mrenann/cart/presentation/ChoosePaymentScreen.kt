package br.mrenann.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.mrenann.cart.presentation.components.ChoosePaymentContent
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class ChoosePaymentScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F8FE))
                    .padding(innerPadding)
            ) {

                ChoosePaymentContent(
                    navigatePop = { navigator.pop() },
                    goToNext = { navigator.push(ChoosePaymentScreen()) },
                )

            }
        }

    }

}
