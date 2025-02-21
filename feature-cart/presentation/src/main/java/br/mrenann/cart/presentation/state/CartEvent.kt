package br.mrenann.cart.presentation.state

import br.mrenann.core.domain.model.Product

sealed class CartEvent {
    object GetProducts : CartEvent()
    object ClearCart : CartEvent()
    data class AddProduct(val product: Product) : CartEvent()
    object CountItems : CartEvent()
}