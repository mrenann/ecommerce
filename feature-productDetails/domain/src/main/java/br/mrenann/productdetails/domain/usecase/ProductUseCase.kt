package br.mrenann.productdetails.domain.usecase

import br.mrenann.core.domain.model.Product

interface ProductUseCase {
    suspend operator fun invoke(params: Params): Product
    data class Params(val id: Int)
}