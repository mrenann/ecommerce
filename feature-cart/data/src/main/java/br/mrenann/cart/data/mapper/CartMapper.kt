package br.mrenann.cart.data.mapper

import br.mrenann.core.data.local.entity.CartItemEntity
import br.mrenann.core.domain.model.Category
import br.mrenann.core.domain.model.Product
import br.mrenann.core.domain.model.ProductCart

fun Product.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        productId = id.toString(),
        name = title,
        price = price.toDouble(),
        quantity = 1,
        imageUrl = images[0]
    )
}

fun ProductCart.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        productId = id.toString(),
        name = title,
        price = price.toDouble(),
        quantity = qtd,
        imageUrl = images[0],
        couponCode = coupon,
    )
}

fun List<CartItemEntity>.toProduct() = map { entity ->
    Product(
        id = entity.productId.toInt(),
        title = entity.name,
        price = entity.price.toInt(),
        description = "",
        category = Category(
            id = 1,
            image = "",
            name = ""
        ),
        images = listOf(entity.imageUrl ?: "")
    )
}

fun List<CartItemEntity>.toProductCart() = map { entity ->
    ProductCart(
        id = entity.productId.toInt(),
        title = entity.name,
        price = entity.price,
        description = "",
        category = Category(
            id = 1,
            image = "",
            name = ""
        ),
        images = listOf(entity.imageUrl ?: ""),
        qtd = entity.quantity,
        priceFinal = entity.totalPrice(),
        coupon = entity.couponCode
    )
}