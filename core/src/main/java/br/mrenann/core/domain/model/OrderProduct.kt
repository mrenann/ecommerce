package br.mrenann.core.domain.model

data class OrderProduct(
    val category: Category = Category(
        id = 0,
        image = "",
        name = ""
    ),
    val id: Int = 0,
    val image: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val priceFinal: Double = 0.0,
    val qtd: Int = 0,
    val title: String = "",
    val images: List<String> = emptyList()
)