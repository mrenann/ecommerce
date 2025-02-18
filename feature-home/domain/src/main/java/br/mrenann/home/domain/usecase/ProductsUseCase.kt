package br.mrenann.home.domain.usecase

import br.mrenann.core.domain.model.Product

interface ProductsUseCase {
    suspend operator fun invoke(): List<Product>
}