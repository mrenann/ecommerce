package br.mrenann.productdetails.data.usecase

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.core.mapper.toMap
import br.mrenann.productdetails.domain.usecase.AddToFavoriteUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddToFavoriteUseCaseImpl(
    private val repository: FavoritesFirestoreRepository
) : AddToFavoriteUseCase {
    override suspend fun invoke(params: AddToFavoriteUseCase.Params): Flow<Unit> {

        return flow {
            val auth = Firebase.auth
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val insert = repository.addFavorite(
                    userId = userId,
                    productId = params.product.id.toString(),
                    productData = params.product.toMap()
                )
                emit(insert)
            }

        }.flowOn(Dispatchers.IO)
    }
}
