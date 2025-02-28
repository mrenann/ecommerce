package br.mrenann.cart.domain.usecase

import kotlinx.coroutines.flow.Flow

interface DecreaseUseCase {
    suspend operator fun invoke(params: Params): Flow<Unit>
    data class Params(val id: String)
}