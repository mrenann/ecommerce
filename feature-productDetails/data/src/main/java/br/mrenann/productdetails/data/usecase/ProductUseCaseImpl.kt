package br.mrenann.productdetails.data.usecase

import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.repository.ProductRepository
import br.mrenann.productdetails.domain.usecase.ProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductUseCaseImpl(
    private val repository: ProductRepository
) : ProductUseCase {

    override suspend fun invoke(params: ProductUseCase.Params): Flow<Product> {
        return flow {
            val details = repository.getProduct(params.id)
            emit(details)
        }.flowOn(Dispatchers.IO)
    }

}