package br.mrenann.cart.domain.usecase

import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow

interface GetProductsFromCartUseCase {
    suspend operator fun invoke(): Flow<List<ProductCart>>
}