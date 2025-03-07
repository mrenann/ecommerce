package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.ClearCouponUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClearCouponUseCaseImpl(
    private val cartRepository: CartRepository
) : ClearCouponUseCase {
    override suspend fun invoke(): Flow<Unit> = flow {
        cartRepository.clearAppliedCoupon()
        emit(Unit) // Emit a Unit to signal completion
    }
}

