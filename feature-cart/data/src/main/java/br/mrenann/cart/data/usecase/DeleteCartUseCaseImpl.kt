package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.DeleteCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteCartUseCaseImpl(
    private val repository: CartRepository
) : DeleteCartUseCase {
    override suspend fun invoke(params: DeleteCartUseCase.Params): Flow<Unit> {
        return flow {
            val exists = repository.exists(params.item.id.toString())
            if (exists) {
                repository.deleteProduct(params.item)
            }

            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

}