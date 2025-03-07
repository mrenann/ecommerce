package br.mrenann.core.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Order(
    val card: String = "",
    val coupon: String = "",
    @ServerTimestamp val createdAt: Date? = null,
    val discount: Double = 0.0,
    val paymentMethid: String = "",
    val price: Double = 0.0,
    val priceFinal: Double = 0.0,
    val products: List<OrderProduct> = emptyList(),
    @ServerTimestamp val deliveredAt: Date? = null,
    val status: String = "",
)
