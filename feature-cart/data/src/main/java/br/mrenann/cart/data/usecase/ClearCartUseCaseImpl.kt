package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.ClearCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ClearCartUseCaseImpl(
    private val repository: CartRepository
) : ClearCartUseCase {
    override suspend fun invoke(): Flow<Unit> {
        return flow {
            val clear = repository.clear()
            emit(clear)
        }.flowOn(Dispatchers.IO)
    }
}