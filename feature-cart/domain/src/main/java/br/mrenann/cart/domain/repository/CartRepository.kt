package br.mrenann.cart.domain.repository

import br.mrenann.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun getCartTotal(): Double
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun exists(productId: String): Boolean
    suspend fun clear()

}