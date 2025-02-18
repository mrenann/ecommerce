package br.mrenann.home.data.source

import br.mrenann.core.data.remote.StoreService
import br.mrenann.core.domain.model.Category
import br.mrenann.home.domain.source.CategoriesDataSource

class CategoriesDataSourceImpl(
    private val service: StoreService
) : CategoriesDataSource {
    override suspend fun getCategories(): List<Category> {
        val response = service.getCategories()

        return response.map { category ->
            Category(
                id = category.id,
                image = category.image,
                name = category.name
            )
        }
    }

}