package br.mrenann.cart.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.Card
import br.mrenann.cart.presentation.R
import br.mrenann.cart.presentation.state.CartState
import br.mrenann.core.domain.model.Category
import br.mrenann.core.domain.model.ProductCart
import br.mrenann.core.domain.model.Purchase
import br.mrenann.core.util.formatBalance
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.CreditCard
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun ConfirmationCard(
    card: Card?,
    paymentMethod: String,
    cartState: CartState,
    replaceAll: () -> Unit,
) {
    var loading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Column {
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(modifier = Modifier) {
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
                            text = "Products(${cartState.itemsCount})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = (cartState.total + cartState.discountApplied).formatBalance(),
                            fontSize = 16.sp,
                        )
                    }
                    if (cartState.discountApplied > 0 && cartState.couponCode.isNullOrBlank()
                            .not()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${cartState.couponCode}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            Text(
                                text = "- " + (cartState.discountApplied).formatBalance(),
                                fontSize = 16.sp,
                                color = Color.Red
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Delivery",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Free",
                            fontSize = 16.sp,
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 6.dp),
                        thickness = .5.dp,
                        color = Color.LightGray
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = cartState.total.formatBalance(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (cartState.discountApplied > 0 && cartState.couponCode.isNullOrBlank()
                                .not()
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 12.dp),
                                text = (cartState.total + cartState.discountApplied).formatBalance(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )

                        }

                    }

                    Column(
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "Pay with",
                            fontSize = 16.sp,
                        )
                        Row(
                            Modifier.padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F0FE)), // Light blue circle background
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    painter = if (card != null) rememberVectorPainter(EvaIcons.Fill.CreditCard) else painterResource(
                                        R.drawable.ic_pix
                                    ),
                                    contentDescription = null, // Provide a meaningful description
                                    modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                                )
                            }
                            Text(
                                text = if (card == null) paymentMethod.uppercase() else "Card **** ${
                                    card.cardNumber.takeLast(
                                        4
                                    )
                                }",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }

                }


            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(10.dp),
                enabled = loading.not(),
                onClick = {
                    loading = true
                    coroutineScope.launch { // Launch a coroutine
                        val db = Firebase.firestore
                        val userId = Firebase.auth.currentUser?.uid
                        if (userId != null) {
                            try {
                                val products = cartState.products
                                val ordersRef = db.collection("users").document(userId)
                                    .collection("orders")
                                    .document()
                                val completeCardData = Purchase(
                                    priceFinal = cartState.total,
                                    price = cartState.total + cartState.discountApplied,
                                    discount = cartState.discountApplied,
                                    coupon = cartState.couponCode ?: "",
                                    products = products,
                                    card = if (card != null) "**** ${card.cardNumber.takeLast(4)}" else null,
                                    paymentMethod = paymentMethod,
                                    createdAt = FieldValue.serverTimestamp(),
                                    status = "awaiting_payment"
                                )
                                ordersRef.set(completeCardData)
                                    .await()


                                if (cartState.couponCode != null && cartState.discountApplied > 0) {
                                    val couponRef =
                                        db.collection("coupons")
                                            .document(cartState.couponCode)
                                    val snapshot = couponRef.get().await()
                                    val data = snapshot.data ?: emptyMap()
                                    val redeemedBy =
                                        (data["redeemedBy"] as? List<*>)?.map { it.toString() }
                                            ?: emptyList()

                                    couponRef.update("redeemedBy", redeemedBy + userId)
                                        .await()
                                }

                                loading = false
                                replaceAll()
                            } catch (e: Exception) {
                                // Handle error
                                loading = false
                                Log.e(
                                    "ConfirmationCard",
                                    "Error confirming purchase: ${e.message}",
                                    e
                                )
                            }
                        }
                    }
                }
            ) {
                if (loading.not()) {
                    Text("Confirm Purchase")
                } else {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun ConfirmationCardPreview() {
    ConfirmationCard(
        card = null,
        paymentMethod = "pix",
        cartState = CartState(
            total = 100.0,
            itemsCount = 2,
            products = listOf<ProductCart>(
                ProductCart(
                    id = 1,
                    title = "Title",
                    price = 10.0,
                    description = "Description",
                    category = Category(
                        id = 1,
                        name = "category"
                    ),
                    images = listOf("image1", "image2"),
                    qtd = 1,
                    priceFinal = 10.0,
                ),
                ProductCart(
                    id = 2,
                    title = "Title",
                    price = 10.0,
                    description = "Description",
                    images = listOf("image1", "image2"),
                    qtd = 1,
                    priceFinal = 10.0,
                    category = Category(
                        id = 1,
                        name = "category"
                    ),

                    ),

                ),
            discountApplied = 10.0,
            couponCode = "DISCOUNT10"
        ),
        replaceAll = {}
    )
}
