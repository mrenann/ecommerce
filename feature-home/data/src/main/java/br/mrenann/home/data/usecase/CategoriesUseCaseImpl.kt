package br.mrenann.home.data.usecase

import br.mrenann.core.domain.model.Category
import br.mrenann.home.domain.repository.CategoriesRepository
import br.mrenann.home.domain.usecase.CategoriesUseCase

class CategoriesUseCaseImpl(
    private val repository: CategoriesRepository
) : CategoriesUseCase {
    override suspend fun invoke(): List<Category> {
        return repository.getCategories()
    }

}