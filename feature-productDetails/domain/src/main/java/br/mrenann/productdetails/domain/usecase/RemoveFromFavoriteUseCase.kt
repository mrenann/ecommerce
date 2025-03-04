package br.mrenann.productdetails.domain.usecase

import kotlinx.coroutines.flow.Flow

interface RemoveFromFavoriteUseCase {
    suspend operator fun invoke(params: Params): Flow<Unit>
    data class Params(val id: Int)
}