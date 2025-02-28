package br.mrenann.cart.presentation.mapper

import br.mrenann.core.domain.model.Product
import br.mrenann.core.domain.model.ProductCart

fun Product.toProductCart(): ProductCart {
    return ProductCart(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        images = images,
        qtd = 1,
        priceFinal = price.toDouble()
    )
}
