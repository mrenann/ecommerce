package br.mrenann.cart.presentation.state

import br.mrenann.core.domain.model.ProductCart

sealed class CartEvent {
    data object GetProducts : CartEvent()
    data object ClearCart : CartEvent()
    data class AddProduct(val product: ProductCart) : CartEvent()
    data object CountItems : CartEvent()
    data class ApplyCoupon(val userId: String, val code: String, val subtotal: Double) : CartEvent()
    data class RemoveProduct(val product: ProductCart) : CartEvent()
    data class increaseQuantity(val id: String) : CartEvent()
    data class decreaseQuantity(val id: String) : CartEvent()
}