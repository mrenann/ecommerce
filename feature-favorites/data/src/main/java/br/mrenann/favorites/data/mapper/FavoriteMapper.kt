package br.mrenann.favorites.data.mapper

import br.mrenann.core.domain.model.Product

fun Product.toMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "title" to title,
        "price" to price,
        "description" to description,
        "categoryId" to category.id,
        "image" to images[0]
    )
}