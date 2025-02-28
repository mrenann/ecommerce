package br.mrenann.cart.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetCartTotalUseCase {
    suspend operator fun invoke(): Flow<Double>
}