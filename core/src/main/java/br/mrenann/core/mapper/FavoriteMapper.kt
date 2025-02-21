package br.mrenann.core.mapper

import br.mrenann.core.domain.model.Category
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

fun Map<String, Any>.toProductOrNull(): Product? {
    return try {
        Product(
            id = (this["id"] as? Long)?.toInt() ?: return null,  // Firestore stores numbers as Long
            title = this["title"] as? String ?: return null,
            price = (this["price"] as? Long)?.toInt() ?: return null,
            description = this["description"] as? String ?: return null,
            category = Category(
                id = (this["categoryId"] as? String)?.toInt() ?: 0,
                image = "",
                name = "TODO()"
            ),
            images = listOf(
                this["image"] as? String ?: return null
            )
        )
    } catch (e: Exception) {
        null
    }
}