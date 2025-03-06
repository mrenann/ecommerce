package br.mrenann.profile.presentation.util

import androidx.compose.ui.graphics.Color

fun String.formatOrderStatus(): Triple<String, String, Color> {
    return when (this) {
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