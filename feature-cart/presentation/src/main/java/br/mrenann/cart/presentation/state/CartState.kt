package br.mrenann.cart.presentation.state

import br.mrenann.core.domain.model.DiscountReason
import br.mrenann.core.domain.model.ProductCart

data class CartState(
    val products: List<ProductCart>,
    val itemsCount: Int,
    val discountApplied: Double = 0.0,
    val total: Double = 0.0,
    val couponCode: String? = null,
    val typeAddress: String? = "",
    val location: String? = "",
    val couponError: DiscountReason? = null
)