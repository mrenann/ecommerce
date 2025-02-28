package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.AddCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddCartUseCaseImpl(
    private val repository: CartRepository
) : AddCartUseCase {
    override suspend fun invoke(params: AddCartUseCase.Params): Flow<Unit> {
        return flow {
            val exists = repository.exists(params.item.id.toString())
            if (exists) {
                repository.addQuantity(params.item.id.toString())
            } else {
                repository.insertProduct(params.item)
            }

            emit(Unit)
        }.flowOn(Dispatchers.IO)
    }

}