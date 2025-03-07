package br.mrenann.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.components.CartItem
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import br.mrenann.core.util.formatBalance
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.CheckmarkCircle2
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Trash

class CartScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<CartScreenModel>()
        val state by screenModel.state.collectAsState()

        var promoCode by remember { mutableStateOf("") }
        var discountPercentage by remember { mutableStateOf(0.0) }

        // Update the discountPercentage whenever the state changes
        LaunchedEffect(state) {
            if (state is CartScreenModel.State.Result) {
                val newDiscount =
                    (state as CartScreenModel.State.Result).state.discountApplied
                if (newDiscount != discountPercentage) {
                    discountPercentage = newDiscount
                }
            }
        }

        val subtotal = state.let {
            if (it is CartScreenModel.State.Result) {
                it.state.products.sumOf { product -> (product.price * product.qtd) }
            } else 0.0
        }
        val deliveryFee = 0.00
        val total = (subtotal - discountPercentage) + deliveryFee



        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = "Cart",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = { screenModel.clearCart() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.Trash,
                            contentDescription = "Clear Cart"
                        )
                    }
                }

                // Cart Items
                when (state) {
                    is CartScreenModel.State.Result -> {
                        val result = state as CartScreenModel.State.Result
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(result.state.products.size) { index ->
                                val product = result.state.products[index]
                                CartItem(
                                    product = product,
                                    onIncrement = {
                                        screenModel.increaseQuantity(product.id.toString())
                                    },
                                    onDecrease = {
                                        screenModel.decreaseQuantity(product.id.toString())
                                    },
                                    remove = {
                                        screenModel.removeProduct(product)
                                    }
                                )
                            }
                        }
                    }

                    else -> {}
                }

                // Promo Code Input and Checkout Summary
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = promoCode,
                        maxLines = 1,
                        minLines = 1,
                        singleLine = true,
                        onValueChange = { promoCode = it },
                        placeholder = { Text("Has Promo Code") },
                        trailingIcon = {
                            IconButton(onClick = {
                                if (promoCode.isNotBlank()) {
                                    screenModel.applyCoupon(
                                        userId = "123",
                                        code = promoCode,
                                        subtotal = subtotal.toDouble()
                                    )
                                    if (state is CartScreenModel.State.Result) {
                                        discountPercentage =
                                            (state as CartScreenModel.State.Result).state.discountApplied
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = EvaIcons.Fill.CheckmarkCircle2,
                                    contentDescription = "Apply Promo",
                                    tint = if (discountPercentage != 0.0) Color(0xFF48D861) else Color.Gray
                                )

                            }
                        },
                        suffix = {
                            if (state is CartScreenModel.State.Result) {
                                val result = state as CartScreenModel.State.Result
                                if (result.state.discountApplied != null) {
                                    Text("PROMO APPLIED")
                                }
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Price Breakdown
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal:", style = MaterialTheme.typography.bodyMedium)
                            Text(subtotal.formatBalance())
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Fee:", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Free", color = Color(0xFF48D861))
                        }
                        if (discountPercentage != 0.0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Discount:", style = MaterialTheme.typography.bodyMedium)
                                Text("- " + discountPercentage.formatBalance())
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Checkout Button


                    if (state is CartScreenModel.State.Result) {
                        val resultState = (state as CartScreenModel.State.Result).state
                        val text =
                            if (resultState.products.isEmpty()) {
                                "Cart Empty"
                            } else {
                                "Checkout for ${total.formatBalance()}"
                            }
                        Button(
                            onClick = { navigator.push(PreparingScreen()) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = resultState.products.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF48D861)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                }
            }
        }
    }


}