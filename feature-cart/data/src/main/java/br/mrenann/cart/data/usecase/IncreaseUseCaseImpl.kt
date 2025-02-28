package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.IncreaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IncreaseUseCaseImpl(
    private val repository: CartRepository
) : IncreaseUseCase {
    override suspend fun invoke(params: IncreaseUseCase.Params): Flow<Unit> {
        return flow {
            repository.addQuantity(params.id)

            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

}