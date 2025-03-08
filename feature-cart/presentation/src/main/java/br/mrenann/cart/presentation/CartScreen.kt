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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.components.CartItem
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import br.mrenann.core.ui.components.SnackBarCustom
import br.mrenann.core.util.formatBalance
import cafe.adriel.lyricist.LocalStrings
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
import kotlinx.coroutines.launch

class CartScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<CartScreenModel>()
        val state by screenModel.state.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        var promoCode by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val strings = LocalStrings.current.cartScreen

        LaunchedEffect(Unit) {
            screenModel.getProducts()
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            snackbarHost = {
                SnackBarCustom(
                    snackbarHostState = snackbarHostState,
                    snackBarState = false
                )
            }
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
                        text = strings.title,
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
                    is CartScreenModel.State.Loading -> {
                        // Show loading indicator
                        CircularProgressIndicator()
                    }

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

                    is CartScreenModel.State.Init -> {
                        Text("Your cart is empty.")
                    }
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
                        placeholder = { Text(strings.hasPromoCode) },
                        trailingIcon = {
                            IconButton(onClick = {
                                if (state is CartScreenModel.State.Result) {
                                    val result = state as CartScreenModel.State.Result

                                    if (promoCode.isNotBlank() && result.state.discountApplied == 0.0) {
                                        screenModel.applyCoupon(
                                            code = promoCode,
                                        )
                                    }


                                    if (result.state.discountApplied > 0) {
                                        screenModel.removeCoupon()
                                    }
                                }
                            }) {
                                val hasDiscount =
                                    ((state as? CartScreenModel.State.Result)?.state?.discountApplied
                                        ?: 0.0) > 0.0
                                Icon(
                                    imageVector = EvaIcons.Fill.CheckmarkCircle2,
                                    contentDescription = "Apply Promo",
                                    tint = if (hasDiscount) Color(0xFF48D861) else Color.Gray
                                )

                            }
                        },
                        suffix = {
                            if (state is CartScreenModel.State.Result) {
                                val result = state as CartScreenModel.State.Result
                                if (result.state.discountApplied != 0.0) {
                                    Text(strings.promoAplied)
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
                    if (state is CartScreenModel.State.Result) {
                        val resultState = (state as CartScreenModel.State.Result).state
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "${strings.subtotal}:",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(resultState.products.sumOf { it.price * it.qtd }
                                    .formatBalance())
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "${strings.deliveryFee}:",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(text = strings.free, color = Color(0xFF48D861))
                            }
                            if (resultState.discountApplied != 0.0) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "${strings.discount}:",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text("- " + resultState.discountApplied.formatBalance())
                                }
                            }

                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        // Checkout Button


                        val text =
                            if (resultState.products.isEmpty()) {
                                strings.cartEmpty
                            } else {
                                strings.checkoutFor(resultState.total.formatBalance())
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

                        LaunchedEffect(state) {
                            val result = (state as CartScreenModel.State.Result).state
                            if (result.couponError != null) {
                                val (errorTitle, errorMessage) = result.couponError.toPair()
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = errorMessage,
                                        duration = SnackbarDuration.Short
                                    )
                                    // Reset the coupon error after showing the Snackbar
                                    screenModel.resetCouponError()
                                }
                            }
                        }

                    }

                }
            }
        }
    }
}