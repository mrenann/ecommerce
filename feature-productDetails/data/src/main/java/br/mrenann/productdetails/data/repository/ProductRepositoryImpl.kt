package br.mrenann.productdetails.data.repository

import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.repository.ProductRepository
import br.mrenann.productdetails.domain.source.ProductDataSource
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource
) : ProductRepository {
    override suspend fun getProduct(id: Int): Product {
        return try {
            remoteDataSource.getProduct(id)
        } catch (e: HttpException) {
            throw Exception("Erro ao buscar o produto: ${e.message()}")
        } catch (e: IOException) {
            throw Exception("Erro de conexão: verifique sua internet")
        }
    }
}
