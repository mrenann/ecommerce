package br.mrenann.cart.presentation

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.components.ChoosePaymentContent
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft
import kotlinx.coroutines.tasks.await

data class Card(
    val cardNumber: String = "",
    val cvv: String = "",
    val expiryDate: String = "",
    val type: String = ""
)

class ChoosePaymentScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var cards by remember { mutableStateOf<List<Card>>(emptyList()) }
        val cartScreenModel = koinScreenModel<CartScreenModel>()
        val cartState by cartScreenModel.state.collectAsState()

        LaunchedEffect(true) {
            val db = Firebase.firestore
            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) {
                val cardRef =
                    db.collection("users").document(userId).collection("cards").get().await()
                cards = cardRef.documents.mapNotNull {
                    it.toObject(Card::class.java)
                }
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = "Payment",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )

                }

                if (cartState is CartScreenModel.State.Result) {
                    val state = (cartState as CartScreenModel.State.Result).state
                    Log.d("CartScreenModel", "Content: $state")
                    ChoosePaymentContent(
                        goToNext = { payment, card ->
                            navigator.push(
                                ConfirmPurchaseScreen(
                                    payment,
                                    card
                                )
                            )
                        },
                        cards = cards,
                        value = state.total,
                        discountValue = state.discountApplied,
                        discountCode = state.couponCode
                    )
                }


            }
        }

    }

}
