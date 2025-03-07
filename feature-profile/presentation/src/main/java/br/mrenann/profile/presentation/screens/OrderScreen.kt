package br.mrenann.profile.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Order
import br.mrenann.core.domain.model.OrderStatus
import br.mrenann.core.domain.model.toOrderStatus
import br.mrenann.core.util.formatToReadableDate
import br.mrenann.navigation.LocalNavigatorParent
import br.mrenann.profile.presentation.R
import br.mrenann.profile.presentation.components.orders.OrderStatusInfo
import br.mrenann.profile.presentation.components.orders.ReceiptView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.CreditCard
import compose.icons.evaicons.fill.Person
import compose.icons.evaicons.fill.Pin
import compose.icons.evaicons.outline.ChevronLeft

data class OrderScreen(
    val order: Order
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val navigatorParent = LocalNavigatorParent.currentOrThrow
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

@Composable
fun DeliveryCard(order: Order, color: Color) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            fontSize = 17.sp,
            text = "Delivery To",
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = EvaIcons.Fill.Person,
                contentDescription = "Search Icon",
                tint = color
            )
            Text(
                text = order.sendTo,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                style = MaterialTheme.typography.bodyLarge,
            )

        }
        Spacer(Modifier.size(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = EvaIcons.Fill.Pin,
                contentDescription = "Search Icon",
                tint = color
            )
            Column {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = order.typeAddress,
                    fontSize = 15.sp,
                    lineHeight = 15.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = order.location,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

        }
    }
}

@Composable
fun PaidCard(order: Order, color: Color) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            fontSize = 17.sp,
            text = "Paid with",
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = if (order.card.isNullOrBlank().not()
                ) rememberVectorPainter(EvaIcons.Fill.CreditCard) else painterResource(
                    R.drawable.ic_pix
                ),
                contentDescription = null, // Provide a meaningful description
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(color)
            )
            Text(
                text = if (order.card.isNullOrBlank()) order.card ?: "Pix" else order.card ?: "",
                fontSize = 14.sp,
                lineHeight = 14.sp,
                style = MaterialTheme.typography.bodyLarge,
            )

        }
    }
}

@Preview
@Composable
fun DeliveryCardPreview() {
    DeliveryCard(
        Order(
            sendTo = "Marcos Renann",
            typeAddress = "Residence",
            location = "Rua dos bobos, 0"
        ), OrderStatus.PAID.color
    )

}