package br.mrenann.productdetails.data.repository

import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.repository.ProductRepository
import br.mrenann.productdetails.domain.source.ProductDataSource

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource
) : ProductRepository {
    override suspend fun getProduct(id: Int): Product {
        return remoteDataSource.getProduct(id)
    }


}