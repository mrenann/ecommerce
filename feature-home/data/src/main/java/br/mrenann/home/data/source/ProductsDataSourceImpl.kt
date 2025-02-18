package br.mrenann.home.data.source

import br.mrenann.core.data.remote.StoreService
import br.mrenann.core.domain.model.Category
import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.source.ProductsDataSource

class ProductsDataSourceImpl(
    private val service: StoreService
) : ProductsDataSource {
    override suspend fun getProducts(): List<Product> {
        val response = service.getProducts()

        return response.map { product ->
            val category = product.category
            Product(
                id = product.id,
                images = product.images,
                price = product.price,
                title = product.title,
                description = product.description,
                category = Category(
                    id = category.id,
                    image = category.image,
                    name = category.name
                )
            )
        }
    }

}