package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.DecreaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DecreaseUseCaseImpl(
    private val repository: CartRepository
) : DecreaseUseCase {
    override suspend fun invoke(params: DecreaseUseCase.Params): Flow<Unit> {
        return flow {
            repository.removeQuantity(params.id)

            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

}