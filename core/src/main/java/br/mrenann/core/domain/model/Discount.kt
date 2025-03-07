package br.mrenann.core.domain.model

data class Discount(
    val code: String = "",
    val discountType: String = "",
    val discountValue: Int = 0,
    val expirationDate: String = "",
    val maxDiscount: Int = 0,
    val maxRedemptions: Int = 0,
    val minPurchase: Int = 0,
    val redeemedBy: List<String> = emptyList()
)