package br.mrenann.home.domain.usecase

import br.mrenann.core.domain.model.Category

interface CategoriesUseCase {
    suspend operator fun invoke(): List<Category>
}