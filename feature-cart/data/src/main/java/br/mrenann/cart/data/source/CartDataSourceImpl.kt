package br.mrenann.cart.data.source

import br.mrenann.cart.data.mapper.toCartItemEntity
import br.mrenann.cart.data.mapper.toProductCart
import br.mrenann.cart.domain.source.CartDataSource
import br.mrenann.core.data.local.dao.AppliedCouponDao
import br.mrenann.core.data.local.dao.CartDao
import br.mrenann.core.data.local.entity.AppliedCouponEntity
import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CartDataSourceImpl(
    private val dao: CartDao,
    private val cupomDao: AppliedCouponDao
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

    override suspend fun applyCoupon(productId: String, discount: Double, coupon: String) {
        dao.applyCoupon(productId, discount, coupon)
    }

    override suspend fun addCoupon(
        code: String,
        discountPercentage: Double?,
        discountAmount: Double?,
        isFixedAmount: Boolean?
    ) {
        cupomDao.insertAppliedCoupon(
            AppliedCouponEntity(
                couponCode = code,
                discountPercentage = discountPercentage,
                discountAmount = discountAmount,
                isFixedAmount = isFixedAmount
            )
        )
    }

    override suspend fun clearAppliedCoupon() {
        cupomDao.clearAppliedCoupon()
    }

    override suspend fun clear() {
        return dao.clearCart()
    }
}