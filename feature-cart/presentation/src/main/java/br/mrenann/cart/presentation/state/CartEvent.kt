package br.mrenann.cart.presentation.state

import br.mrenann.core.domain.model.ProductCart

sealed class CartEvent {
    object GetProducts : CartEvent()
    object ClearCart : CartEvent()
    data class AddProduct(val product: ProductCart) : CartEvent()
    object CountItems : CartEvent()
    data class ApplyCoupon(val userId: String, val code: String, val subtotal: Double) : CartEvent()


}