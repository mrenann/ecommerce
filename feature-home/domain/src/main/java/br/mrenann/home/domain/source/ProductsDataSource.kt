package br.mrenann.home.domain.source

import br.mrenann.core.domain.model.Product

interface ProductsDataSource {
    suspend fun getProducts(): List<Product>
    suspend fun getProductsByCategory(id: String): List<Product>
    suspend fun getProductsByQuery(query: String): List<Product>
}