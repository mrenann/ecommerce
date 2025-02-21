package br.mrenann.favorites.presentation.state

import br.mrenann.core.domain.model.Product

data class FavoriteState(
    val products: List<Product>,
)