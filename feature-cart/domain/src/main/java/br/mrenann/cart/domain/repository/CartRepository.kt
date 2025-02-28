package br.mrenann.cart.domain.repository

import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getProducts(): Flow<List<ProductCart>>
    suspend fun getCartTotal(): Double
    suspend fun insertProduct(product: ProductCart)
    suspend fun addQuantity(productId: String)
    suspend fun removeQuantity(productId: String)
    suspend fun deleteProduct(product: ProductCart)
    suspend fun exists(productId: String): Boolean
    suspend fun clear()

}