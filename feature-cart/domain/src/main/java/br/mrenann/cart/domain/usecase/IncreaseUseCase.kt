package br.mrenann.cart.domain.usecase

import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow

interface IncreaseUseCase {
    suspend operator fun invoke(params: Params): Flow<Unit>
    data class Params(val id: String)
}