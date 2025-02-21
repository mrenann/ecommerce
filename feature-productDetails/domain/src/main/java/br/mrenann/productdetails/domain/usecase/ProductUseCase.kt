package br.mrenann.productdetails.domain.usecase

import br.mrenann.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {
    suspend operator fun invoke(params: Params): Flow<Product>
    data class Params(val id: Int)
}