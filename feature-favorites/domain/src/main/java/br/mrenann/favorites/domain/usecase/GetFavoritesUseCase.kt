package br.mrenann.favorites.domain.usecase

import br.mrenann.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface GetFavoritesUseCase {
    suspend operator fun invoke(): Flow<List<Product>>
}