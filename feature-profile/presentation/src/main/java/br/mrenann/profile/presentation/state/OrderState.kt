package br.mrenann.profile.presentation.state

import br.mrenann.core.domain.model.Order

data class OrderState(
    val order: List<Order> = emptyList()
)