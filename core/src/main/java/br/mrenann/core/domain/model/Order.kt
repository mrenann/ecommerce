package br.mrenann.core.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Order(
    val priceFinal: Double = 0.0,
    val status: String = "",
    val coupon: String = "",
    val products: List<OrderProduct> = emptyList(),
    @ServerTimestamp val createdAt: Date? = null // Add this field to store the created date
)
