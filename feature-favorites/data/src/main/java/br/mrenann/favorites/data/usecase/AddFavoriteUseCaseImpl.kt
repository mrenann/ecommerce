package br.mrenann.favorites.data.usecase

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.core.mapper.toMap
import br.mrenann.favorites.domain.usecase.AddFavoriteUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddFavoriteUseCaseImpl(
    private val repository: FavoritesFirestoreRepository
) : AddFavoriteUseCase {
    override suspend fun invoke(params: AddFavoriteUseCase.Params): Flow<Unit> {

        return flow {
            val auth = Firebase.auth
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val insert = repository.addFavorite(
                    userId = userId,
                    productId = params.item.id.toString(),
                    productData = params.item.toMap()
                )
                emit(insert)
            }

        }.flowOn(Dispatchers.IO)
    }

}