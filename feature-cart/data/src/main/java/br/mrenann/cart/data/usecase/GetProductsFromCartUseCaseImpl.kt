package br.mrenann.cart.data.usecase

import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.GetProductsFromCartUseCase
import br.mrenann.core.domain.model.ProductCart
import kotlinx.coroutines.flow.Flow

class GetProductsFromCartUseCaseImpl(
    private val repository: CartRepository
) : GetProductsFromCartUseCase {
    override suspend fun invoke(): Flow<List<ProductCart>> {
        return repository.getProducts()
    }
}