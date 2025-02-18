package br.mrenann.home.domain.source

import br.mrenann.core.domain.model.Category

interface CategoriesDataSource {
    suspend fun getCategories(): List<Category>
}