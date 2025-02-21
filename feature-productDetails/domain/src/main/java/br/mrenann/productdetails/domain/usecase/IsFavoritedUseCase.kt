package br.mrenann.productdetails.domain.usecase

import kotlinx.coroutines.flow.Flow

interface IsFavoritedUseCase {
    suspend operator fun invoke(params: Params): Flow<Boolean>
    data class Params(val id: Int)
}