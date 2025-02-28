package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.GetCartTotalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCartTotalUseCaseImpl(
    private val repository: CartRepository
) : GetCartTotalUseCase {
    override suspend fun invoke(): Flow<Double> {
        return flow {
            val clear = repository.getCartTotal()
            emit(clear)
        }.flowOn(Dispatchers.IO)
    }

}