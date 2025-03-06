package br.mrenann.core.domain.model

import androidx.compose.ui.graphics.Color


enum class OrderStatus(
    val displayName: String,
    val description: String,
    val color: Color
) {
    AWAITING_PAYMENT(
        "Awaiting Payment",
        "Your payment has not been received yet.",
        Color(0xFFFFA500) // Orange
    ),
    PAYMENT_CANCELLED(
        "Payment Cancelled",
        "The payment has been cancelled.",
        Color(0xFFFF0000) // Red
    ),
    PAID(
        "Paid",
        "Your order has been paid and is being processed.",
        Color(0xFF4CAF50) // Green
    ),
    ON_THE_WAY(
        "On the Way",
        "Your order has been shipped.",
        Color(0xFF2196F3) // Blue
    ),
    DELIVERED(
        "Delivered",
        "Your order has been successfully delivered.",
        Color(0xFF673AB7) // Purple
    ),
    UNKNOWN_STATUS(
        "Unknown Status",
        "We couldn't determine the order status.",
        Color.Gray
    );

    fun toTriple(): Triple<String, String, Color> {
        return Triple(displayName, description, color)
    }
}

fun String.formatOrderStatus(): Triple<String, String, Color> {
    val cleanedStatus = this.replace(" ", "").uppercase()

    if (cleanedStatus.isEmpty()) {
        return OrderStatus.UNKNOWN_STATUS.toTriple()
    }

    return try {
        OrderStatus.valueOf(cleanedStatus).toTriple()
    } catch (e: IllegalArgumentException) {
        OrderStatus.UNKNOWN_STATUS.toTriple()
    }
}

fun String.toOrderStatus(): OrderStatus {
    val cleanedStatus = this.replace(" ", "").uppercase()

    if (cleanedStatus.isEmpty()) {
        return OrderStatus.UNKNOWN_STATUS
    }

    return try {
        OrderStatus.valueOf(cleanedStatus)
    } catch (e: IllegalArgumentException) {
        OrderStatus.UNKNOWN_STATUS
    }
}

