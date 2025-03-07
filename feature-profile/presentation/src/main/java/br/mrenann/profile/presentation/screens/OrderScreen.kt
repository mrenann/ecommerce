package br.mrenann.profile.presentation.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Order
import br.mrenann.core.domain.model.OrderStatus
import br.mrenann.core.domain.model.toOrderStatus
import br.mrenann.core.util.formatToReadableDate
import br.mrenann.profile.presentation.components.DeliveryCard
import br.mrenann.profile.presentation.components.PaidCard
import br.mrenann.profile.presentation.components.orders.OrderStatusInfo
import br.mrenann.profile.presentation.components.orders.ReceiptView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft

data class OrderScreen(
    val order: Order
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val orderStatus = order.status.toOrderStatus()
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1F),
                        text = "Order Details",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {

                    OrderStatusInfo(
                        status = orderStatus,
                        subtitle = if (order.deliveredAt != null && orderStatus == OrderStatus.DELIVERED) {
                            order.deliveredAt?.formatToReadableDate() ?: ""
                        } else if (orderStatus == OrderStatus.UNKNOWN_STATUS) {
                            "We're checking on this for you"
                        } else if (order.paidAt != null && orderStatus == OrderStatus.PAID) {
                            order.paidAt?.formatToReadableDate() ?: ""
                        } else if (order.cancelledAt != null && orderStatus == OrderStatus.PAYMENT_CANCELLED) {
                            order.cancelledAt?.formatToReadableDate() ?: ""
                        } else {
                            ""
                        }
                    )
                }

                ReceiptView(
                    order = order,
                    totalAmount = "25 $",
                    contentColor = Color.DarkGray
                )

                DeliveryCard(order, orderStatus.color)
                if (order.paidAt != null) PaidCard(order, orderStatus.color)

            }
        }
    }

}