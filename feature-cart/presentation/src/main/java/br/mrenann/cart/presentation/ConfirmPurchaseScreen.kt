package br.mrenann.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import br.mrenann.core.domain.model.Purchase
import br.mrenann.core.util.formatBalance
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
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


                Column {
                    Card(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Column(modifier = Modifier.background(Color.White)) {
                            // Title and Icon
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .padding(top = 12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Payment Method",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Column {
                                        Text(
                                            text = if (card == null) paymentMethod else "Card **** ${
                                                card.cardNumber.takeLast(
                                                    4
                                                )
                                            }", color = Color(0xFF098D19)
                                        )
                                        Text(
                                            text = 128.formatBalance(), color = Color(0xFF098D19)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(12.dp))
                            }


                        }

                        if (cartState is CartScreenModel.State.Result) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    val db = Firebase.firestore
                                    val userId = Firebase.auth.currentUser?.uid
                                    if (userId != null) {
                                        val products =
                                            (cartState as CartScreenModel.State.Result).state.products
                                        val ordersRef =
                                            db.collection("users").document(userId)
                                                .collection("orders")
                                                .document()
                                        val completeCardData = Purchase(
                                            priceFinal = (cartState as CartScreenModel.State.Result).state.total,
                                            coupon = "",
                                            products = products,
                                            createdAt = FieldValue.serverTimestamp(),
                                            status = "awaiting_payment"
                                        )
                                        ordersRef.set(completeCardData)
                                            .addOnSuccessListener {
                                                cartScreenModel.clearCart()
                                                navigator.replaceAll(PixScreen())
                                            }
                                            .addOnFailureListener {
                                                // Handle error
                                            }
                                    }

                                }
                            ) {
                                Text("Confirm Purchase")
                            }
                        }

                    }


                }

            }
        }
    }

}