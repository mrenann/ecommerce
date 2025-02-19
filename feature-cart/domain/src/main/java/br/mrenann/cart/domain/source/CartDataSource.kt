package br.mrenann.cart.domain.source

import br.mrenann.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getProducts(): Flow<List<Product>>
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun exists(productId: String): Boolean
    suspend fun clear()

}