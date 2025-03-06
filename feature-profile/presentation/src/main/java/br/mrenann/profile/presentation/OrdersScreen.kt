package br.mrenann.profile.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Order
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class OrdersScreen : Screen { // Pass the orders list

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var orders by remember { mutableStateOf<List<Order>>(emptyList()) }

        LaunchedEffect(true) {
            val db = Firebase.firestore
            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) {
                val orderRef =
                    db.collection("users").document(userId).collection("orders").get().await()
                orders = orderRef.documents.mapNotNull {
                    it.toObject(Order::class.java)
                }.sortedByDescending { it.createdAt }
                Log.i("Firestore", "Fetched Cards: $orders")
            }
        }

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
                        text = "Orders",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                }

                OrderList(orders = orders) // Display the list of orders
            }
        }
    }
}

fun formatOrderStatus(status: String): Triple<String, String, Color> {
    return when (status) {
        "awaiting_payment" -> Triple(
            "Awaiting Payment",
            "Your payment has not been received yet.",
            Color(0xFFFFA500)
        ) // Orange
        "payment_cancelled" -> Triple(
            "Payment Cancelled",
            "The payment has been cancelled.",
            Color(0xFFFF0000)
        ) // Red
        "paid" -> Triple(
            "Paid",
            "Your order has been paid and is being processed.",
            Color(0xFF4CAF50)
        ) // Green
        "on_the_way" -> Triple(
            "On the Way",
            "Your order has been shipped.",
            Color(0xFF2196F3)
        ) // Blue
        "received" -> Triple(
            "Delivered",
            "Your order has been successfully delivered.",
            Color(0xFF673AB7)
        ) // Purple
        else -> Triple("Unknown Status", "We couldn't determine the order status.", Color.Gray)
    }
}


@Composable
fun OrderList(orders: List<Order>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(orders) { order ->
            OrderCard(order = order)
            Spacer(modifier = Modifier.height(8.dp)) // Add spacing between cards
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(),

        ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            val formattedDate = order.createdAt?.let {
                val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                format.format(it)
            } ?: "Data não disponível"
            val (title, subtitle, color) = formatOrderStatus(order.status)

            Text(
                modifier = Modifier.padding(8.dp),
                text = "$formattedDate",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(Modifier, 1.dp, Color.LightGray)
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(order.products[0].images[0])
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = color
                    )

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "${order.products.size} product",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        }
    }
}