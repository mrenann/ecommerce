package br.mrenann.home.data.usecase

import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.repository.ProductsRepository
import br.mrenann.home.domain.usecase.ProductsUseCase

class ProductsUseCaseImpl(
    private val repository: ProductsRepository
) : ProductsUseCase {
    override suspend fun invoke(): List<Product> {
        return repository.getProducts()
    }

}