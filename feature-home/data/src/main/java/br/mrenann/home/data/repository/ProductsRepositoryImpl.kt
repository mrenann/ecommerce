package br.mrenann.home.data.repository

import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.repository.ProductsRepository
import br.mrenann.home.domain.source.ProductsDataSource

class ProductsRepositoryImpl(
    private val remoteDataSource: ProductsDataSource
) : ProductsRepository {
    override suspend fun getProducts(): List<Product> {
        return remoteDataSource.getProducts()
    }

    override suspend fun getProductsByCategory(id: String): List<Product> {
        return remoteDataSource.getProductsByCategory(id)
    }

}