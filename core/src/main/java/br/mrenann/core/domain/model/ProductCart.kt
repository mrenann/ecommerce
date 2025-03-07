package br.mrenann.core.domain.model

data class ProductCart(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: Category,
    val images: List<String>,
    val qtd: Int,
    val priceFinal: Double,
    val coupon: String? = ""
)

