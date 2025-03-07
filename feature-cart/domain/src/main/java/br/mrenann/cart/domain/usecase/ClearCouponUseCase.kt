package br.mrenann.cart.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ClearCouponUseCase {
    suspend operator fun invoke(): Flow<Unit>
}