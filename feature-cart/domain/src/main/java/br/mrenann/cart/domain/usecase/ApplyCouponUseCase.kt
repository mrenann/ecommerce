package br.mrenann.cart.domain.usecase

import br.mrenann.core.domain.model.Discount
import kotlinx.coroutines.flow.Flow

interface ApplyCouponUseCase {
    suspend operator fun invoke(params: Params): Flow<Result>
    data class Params(val userId: String, val code: String, val subtotal: Double)

    sealed class Result {
        data class Success(val discount: Discount, val discountAmount: Double) : Result()
        object Invalid : Result()
    }
}