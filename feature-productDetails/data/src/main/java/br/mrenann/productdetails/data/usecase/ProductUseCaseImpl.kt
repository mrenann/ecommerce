package br.mrenann.productdetails.data.usecase

import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.repository.ProductRepository
import br.mrenann.productdetails.domain.usecase.ProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class ProductUseCaseImpl(
    private val repository: ProductRepository
) : ProductUseCase {

    override suspend fun invoke(params: ProductUseCase.Params): Flow<Result<Product>> {
        return flow {
            try {
                val product = repository.getProduct(params.id)
                emit(Result.success(product))
            } catch (e: HttpException) {
                emit(Result.failure(Exception("Erro HTTP: ${e.code()} - ${e.message()}")))
            } catch (e: IOException) {
                emit(Result.failure(Exception("Falha na conexão com o servidor.")))
            } catch (e: Exception) {
                emit(Result.failure(Exception("Erro desconhecido: ${e.message}")))
            }
        }.flowOn(Dispatchers.IO)
    }

}