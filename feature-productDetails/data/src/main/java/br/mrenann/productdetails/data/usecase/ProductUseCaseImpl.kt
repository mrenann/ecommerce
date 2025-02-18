package br.mrenann.productdetails.data.usecase

import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.repository.ProductRepository
import br.mrenann.productdetails.domain.usecase.ProductUseCase

class ProductUseCaseImpl(
    private val repository: ProductRepository
) : ProductUseCase {

    override suspend fun invoke(params: ProductUseCase.Params): Product {
        return repository.getProduct(params.id)
    }

}