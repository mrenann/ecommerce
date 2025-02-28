package br.mrenann.cart.domain.source

import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getProducts(): Flow<List<ProductCart>>
    suspend fun getCartTotal(): Double
    suspend fun insertProduct(product: ProductCart)
    suspend fun deleteProduct(product: ProductCart)
    suspend fun exists(productId: String): Boolean
    suspend fun clear()

}