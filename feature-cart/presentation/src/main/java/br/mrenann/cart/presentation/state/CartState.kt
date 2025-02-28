package br.mrenann.cart.presentation.state

import br.mrenann.core.domain.model.ProductCart

data class CartState(
    val products: List<ProductCart>,
    val itemsCount: Int,
    val discountApplied: Double? = null,
    val total: Double = 0.0
)