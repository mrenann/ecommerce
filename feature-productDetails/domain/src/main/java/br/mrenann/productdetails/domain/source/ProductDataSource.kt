package br.mrenann.productdetails.domain.source

import br.mrenann.core.domain.model.Product

interface ProductDataSource {
    suspend fun getProduct(id: Int): Product
}