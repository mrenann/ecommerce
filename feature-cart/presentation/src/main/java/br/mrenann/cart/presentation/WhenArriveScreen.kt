package br.mrenann.cart.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.mrenann.cart.presentation.components.ArriveContent
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class WhenArriveScreen(
    val typeAddress: String,
    val address: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val cartScreenModel = koinScreenModel<CartScreenModel>()
        cartScreenModel.setAddress(
            typeAddress = typeAddress,
            address = address
        )
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {


                ArriveContent(
                    navigatePop = { navigator.pop() },
                    goToNext = { navigator.push(ChoosePaymentScreen()) },
                    position = typeAddress
                )

            }
        }

    }

}
