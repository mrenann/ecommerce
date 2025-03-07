package br.mrenann.core.domain.model

import com.google.firebase.firestore.FieldValue

data class Purchase(
    val priceFinal: Double = 0.0,
    val price: Double = 0.0,
    val discount: Double = 0.0,
    val coupon: String = "",
    val products: List<ProductCart> = emptyList(),
    val createdAt: FieldValue,
    val status: String = "",
    val paymentMethod: String = "",
    val card: String? = "",
    val typeAddress: String = "",
    val location: String = "",
    val sendTo: String = "",
)

