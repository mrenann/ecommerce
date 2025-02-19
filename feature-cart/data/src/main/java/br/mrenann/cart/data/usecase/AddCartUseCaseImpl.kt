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
            val insert = repository.insertProduct(params.item)
            emit(insert)
        }.flowOn(Dispatchers.IO)
    }

}