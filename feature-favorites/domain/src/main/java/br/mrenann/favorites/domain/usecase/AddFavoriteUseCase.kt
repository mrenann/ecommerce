package br.mrenann.favorites.domain.usecase

import br.mrenann.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface AddFavoriteUseCase {
    suspend operator fun invoke(params: Params): Flow<Unit>
    data class Params(val item: Product)
}