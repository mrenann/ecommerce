package br.mrenann.home.data.repository

import br.mrenann.core.domain.model.Category
import br.mrenann.home.domain.repository.CategoriesRepository
import br.mrenann.home.domain.source.CategoriesDataSource

class CategoriesRepositoryImpl(
    private val remoteDataSource: CategoriesDataSource
) : CategoriesRepository {
    override suspend fun getCategories(): List<Category> {
        return remoteDataSource.getCategories()
    }

}