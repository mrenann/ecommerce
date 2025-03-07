package br.mrenann.cart.data.repository

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.source.CartDataSource
import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val dataSource: CartDataSource
) : CartRepository {
    override fun getProducts(): Flow<List<ProductCart>> {
        return dataSource.getProducts()
    }

    override suspend fun getCartTotal(): Double {
        return dataSource.getCartTotal()
    }

    override suspend fun insertProduct(product: ProductCart) {
        return dataSource.insertProduct(product)
    }

    override suspend fun addQuantity(productId: String) {
        return dataSource.addQuantity(productId)
    }

    override suspend fun removeQuantity(productId: String) {
        return dataSource.removeQuantity(productId)
    }

    override suspend fun deleteProduct(product: ProductCart) {
        return dataSource.deleteProduct(product)
    }

    override suspend fun applyCoupon(productId: String, discount: Double, coupon: String) {
        return dataSource.applyCoupon(productId, discount, coupon)
    }

    override suspend fun exists(productId: String): Boolean {
        return dataSource.exists(productId)
    }

    override suspend fun clear() {
        return dataSource.clear()
    }
}