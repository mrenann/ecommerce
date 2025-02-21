package br.mrenann.favorites.presentation.state

import br.mrenann.core.domain.model.Product

sealed class FavoriteEvent {
    data class AddProduct(val product: Product) : FavoriteEvent()
}