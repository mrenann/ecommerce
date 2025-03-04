package br.mrenann.productdetails.presentation.state

import br.mrenann.core.domain.model.Product

sealed class DetailsEvent {
    data class GetDetails(val id: Int) : DetailsEvent()
    data class AddFavorite(val product: Product) : DetailsEvent()
    data class CheckedFavorite(val id: Int) : DetailsEvent()
    data class RemoveFavorite(val id: Int) : DetailsEvent()
}