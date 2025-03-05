package br.mrenann.cart.data.source

import br.mrenann.cart.data.mapper.toCartItemEntity
import br.mrenann.cart.data.mapper.toProductCart
import br.mrenann.cart.domain.source.CartDataSource
import br.mrenann.core.data.local.dao.CartDao
import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CartDataSourceImpl(
    private val dao: CartDao
) : CartDataSource {
    override fun getProducts(): Flow<List<ProductCart>> {
        return dao.getCartItems().map {
            it.toProductCart()
        }
    }

    override suspend fun getCartTotal(): Double {
        val items = dao.getCartItems().first()
        return items.sumOf { it.totalPrice() }
    }

    override suspend fun insertProduct(product: ProductCart) {
        dao.addToCart(product.toCartItemEntity())
    }

    override suspend fun deleteProduct(product: ProductCart) {
        dao.removeFromCart(product.id.toString())
    }

    override suspend fun addQuantity(productId: String) {
        dao.increaseQuantity(productId)
    }

    override suspend fun removeQuantity(productId: String) {
        dao.decreaseQuantity(productId)
    }

    override suspend fun exists(productId: String): Boolean {
        return dao.inList(productId) != null
    }

    override suspend fun clear() {
        return dao.clearCart()
    }
}