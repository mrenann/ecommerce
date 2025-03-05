package br.mrenann.core.domain.model

import com.google.firebase.firestore.FieldValue

data class Purchase(
    val priceFinal: Double = 0.0,
    val coupon: String = "",
    val products: List<ProductCart> = emptyList(),
    val createdAt: FieldValue,
    val status: String,
)

