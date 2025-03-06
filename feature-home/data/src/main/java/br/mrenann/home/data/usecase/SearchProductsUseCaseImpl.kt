package br.mrenann.home.data.usecase

import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.repository.ProductsRepository
import br.mrenann.home.domain.usecase.SearchProductsUseCase

class SearchProductsUseCaseImpl(
    private val repository: ProductsRepository
) : SearchProductsUseCase {
    override suspend fun invoke(params: SearchProductsUseCase.Params): List<Product> {
        return repository.getProductsByQuery(params.query)
    }

}