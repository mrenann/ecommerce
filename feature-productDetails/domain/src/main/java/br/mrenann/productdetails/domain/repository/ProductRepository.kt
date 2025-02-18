package br.mrenann.productdetails.domain.repository

import br.mrenann.core.domain.model.Product

interface ProductRepository {
    suspend fun getProduct(id: Int): Product
}