package br.mrenann.home.data.source

import br.mrenann.core.data.remote.StoreService
import br.mrenann.core.data.remote.model.ProductItem
import br.mrenann.core.domain.model.Category
import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.source.ProductsDataSource

class ProductsDataSourceImpl(
    private val service: StoreService
) : ProductsDataSource {

    override suspend fun getProducts(): List<Product> {
        return service.getProducts().map { it.toDomain() }
    }

    override suspend fun getProductsByCategory(id: String): List<Product> {
        return service.getProductsByCategory(id.toInt()).map { it.toDomain() }
    }

    override suspend fun getProductsByQuery(query: String): List<Product> {
        return service.getProductsByTitle(query).map { it.toDomain() }
    }


    private fun ProductItem.toDomain(): Product {
        return Product(
            id = this.id,
            images = this.images,
            price = this.price,
            title = this.title,
            description = this.description,
            category = Category(
                id = this.category.id,
                image = this.category.image,
                name = this.category.name
            )
        )
    }

}