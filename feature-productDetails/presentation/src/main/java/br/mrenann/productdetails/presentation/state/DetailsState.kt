package br.mrenann.productdetails.presentation.state

import br.mrenann.core.domain.model.Product

data class DetailsState(
    val movie: Product? = null,
    val error: String = "",
    val isLoading: Boolean = false,
    val checked: Boolean = false,
)
