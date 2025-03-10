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
    suspend fun applyCoupon(productId: String, discount: Double, coupon: String)
    suspend fun exists(productId: String): Boolean
    suspend fun clear()
    suspend fun addCoupon(
        code: String,
        discountPercentage: Double?,
        discountAmount: Double?,
        isFixedAmount: Boolean?
    )

    suspend fun clearAppliedCoupon()

}