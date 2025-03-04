package br.mrenann.productdetails.data.usecase

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.productdetails.domain.usecase.RemoveFromFavoriteUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoveFromFavoriteUseCaseImpl(
    private val repository: FavoritesFirestoreRepository
) : RemoveFromFavoriteUseCase {
    override suspend fun invoke(params: RemoveFromFavoriteUseCase.Params): Flow<Unit> = flow {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@flow

        repository.removeFavorite(userId, params.id.toString())

        emit(Unit)
    }.flowOn(Dispatchers.IO)
}
