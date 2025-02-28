package br.mrenann.home.data.usecase

import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.repository.ProductsRepository
import br.mrenann.home.domain.usecase.ProductsByCategoryUseCase

class ProductsByCategoryUseCaseImpl(
    private val repository: ProductsRepository
) : ProductsByCategoryUseCase {
    override suspend fun invoke(params: ProductsByCategoryUseCase.Params): List<Product> {
        return repository.getProductsByCategory(params.id)
    }

}