package br.mrenann.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.components.ConfirmationCard
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft

data class ConfirmPurchaseScreen(
    val paymentMethod: String,
    val card: Card? = null
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val cartScreenModel = koinScreenModel<CartScreenModel>()
        val cartState by cartScreenModel.state.collectAsState()

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            tint = Color.Black,
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = "Confirmation",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )

                }


                if (cartState is CartScreenModel.State.Result) {
                    val resultState = (cartState as CartScreenModel.State.Result).state
                    ConfirmationCard(
                        card = card,
                        paymentMethod = paymentMethod,
                        cartState = resultState
                    ) {
                        cartScreenModel.clearCart()
                        navigator.replace(PixScreen(
                            total = resultState.total
                        ))
                    }


                }

            }
        }
    }


}