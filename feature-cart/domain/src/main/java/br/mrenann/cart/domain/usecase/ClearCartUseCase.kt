package br.mrenann.cart.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ClearCartUseCase {
    suspend operator fun invoke(): Flow<Unit>
}