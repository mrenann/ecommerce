package br.mrenann.home.domain.repository

import br.mrenann.core.domain.model.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductsByCategory(id: String): List<Product>
}