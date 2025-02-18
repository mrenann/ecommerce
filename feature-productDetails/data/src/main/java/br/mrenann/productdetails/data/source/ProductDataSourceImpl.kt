package br.mrenann.productdetails.data.source

import br.mrenann.core.data.remote.StoreService
import br.mrenann.core.domain.model.Category
import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.source.ProductDataSource

class ProductDataSourceImpl(
    private val service: StoreService
) : ProductDataSource {

    override suspend fun getProduct(id: Int): Product {
        val response = service.getProductById(id)
        val category = response.category
        return Product(
            id = response.id,
            images = response.images,
            price = response.price,
            title = response.title,
            description = response.description,
            category = Category(
                id = category.id,
                image = category.image,
                name = category.name
            ),
        )

    }

}