package br.mrenann.core.domain.model

data class Purchase(
    val priceFinal: Double = 0.0,
    val coupon: String = "",
    val products: List<ProductCart> = emptyList(),
)

