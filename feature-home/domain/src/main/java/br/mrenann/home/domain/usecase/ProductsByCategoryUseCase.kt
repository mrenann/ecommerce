package br.mrenann.home.domain.usecase

import br.mrenann.core.domain.model.Product

interface ProductsByCategoryUseCase {
    suspend operator fun invoke(params: Params): List<Product>
    data class Params(val id: String)

}
