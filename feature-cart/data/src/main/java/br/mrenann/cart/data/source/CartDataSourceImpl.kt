package br.mrenann.cart.data.source

import br.mrenann.cart.data.mapper.toCartItemEntity
import br.mrenann.cart.data.mapper.toProduct
import br.mrenann.cart.domain.source.CartDataSource
import br.mrenann.core.data.local.dao.CartDao
import br.mrenann.core.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartDataSourceImpl(
    private val dao: CartDao
) : CartDataSource {
    override fun getProducts(): Flow<List<Product>> {
        return dao.getCartItems().map {
            it.toProduct()
        }
    }

    override suspend fun insertProduct(product: Product) {
        dao.addToCart(product.toCartItemEntity())
    }

    override suspend fun deleteProduct(product: Product) {
        dao.removeFromCart(product.toCartItemEntity())
    }

    override suspend fun exists(productId: String): Boolean {
        return dao.inList(productId) != null
    }

    override suspend fun clear() {
        return dao.clearCart()
    }
}