package br.mrenann.productdetails.domain.usecase

import br.mrenann.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface AddToFavoriteUseCase {
    suspend operator fun invoke(params: Params): Flow<Unit>
    data class Params(val product: Product)
}