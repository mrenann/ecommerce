package br.mrenann.core.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val category: Category,
    val images: List<String>
)

