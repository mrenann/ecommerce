package br.mrenann.core.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Order(
    val card: String = "",
    val coupon: String = "",
    @ServerTimestamp val createdAt: Date? = null,
    val discount: Double = 0.0,
    val location: String = "",
    val paymentMethid: String = "",
    val price: Double = 0.0,
    val priceFinal: Double = 0.0,
    val products: List<OrderProduct> = emptyList(),
    val sendTo: String = "",
    @ServerTimestamp val deliveredAt: Date? = null,
    @ServerTimestamp val paidAt: Date? = null,
    @ServerTimestamp val cancelledAt: Date? = null,
    val status: String = "",
    val typeAddress: String = "",
)
