package br.mrenann.home.domain.repository

import br.mrenann.core.domain.model.Category

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
}