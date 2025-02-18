package br.mrenann.core.data.remote

import br.mrenann.core.data.remote.model.ProductItem
import br.mrenann.core.data.remote.response.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreService {
    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @GET("products/{product_id}")
    suspend fun getProductById(
        @Path("product_id") id: Int,
    ): ProductItem
}