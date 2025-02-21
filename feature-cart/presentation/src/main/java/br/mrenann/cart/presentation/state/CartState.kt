package br.mrenann.cart.presentation.state

import br.mrenann.core.domain.model.Product

data class CartState(
    val products: List<Product>,
    val itemsCount: Int,
    val discountApplied: Double? = null,
)