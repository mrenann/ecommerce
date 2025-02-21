package br.mrenann.favorites.data.usecase

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.core.domain.model.Product
import br.mrenann.core.mapper.toProductOrNull
import br.mrenann.favorites.domain.usecase.GetFavoritesUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetFavoritesUseCaseImpl(
    private val repository: FavoritesFirestoreRepository
) : GetFavoritesUseCase {
    override suspend fun invoke(): Flow<List<Product>> = flow {
        val auth = Firebase.auth
        val userId = auth.currentUser?.uid ?: return@flow

        val favoritesData = repository.getFavorites(userId)

        // Convert Firestore map data back to Product objects
        val favoritesList = favoritesData.mapNotNull { it.toProductOrNull() }

        emit(favoritesList)
    }.flowOn(Dispatchers.IO)
}
