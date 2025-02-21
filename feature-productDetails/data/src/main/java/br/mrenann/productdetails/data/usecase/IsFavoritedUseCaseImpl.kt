package br.mrenann.productdetails.data.usecase

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.productdetails.domain.usecase.IsFavoritedUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IsFavoritedUseCaseImpl(
    private val repository: FavoritesFirestoreRepository
) : IsFavoritedUseCase {
    override suspend fun invoke(params: IsFavoritedUseCase.Params): Flow<Boolean> = flow {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@flow emit(false)

        val favoritesData = repository.getFavorites(userId)

        // Check if the product ID exists in the favorites
        val isFavorited = favoritesData.any { it["id"] == params.id.toLong() }

        emit(isFavorited)
    }.flowOn(Dispatchers.IO)
}
