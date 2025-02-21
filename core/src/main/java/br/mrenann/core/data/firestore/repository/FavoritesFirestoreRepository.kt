package br.mrenann.core.data.firestore.repository

import br.mrenann.core.data.firestore.FirebaseService

class FavoritesFirestoreRepository(private val firebaseService: FirebaseService) {
    suspend fun addFavorite(userId: String, productId: String, productData: Map<String, Any>) {
        firebaseService.addFavorite(userId, productId, productData)
    }

    suspend fun removeFavorite(userId: String, productId: String) {
        firebaseService.removeFavorite(userId, productId)
    }

    suspend fun getFavorites(userId: String): List<Map<String, Any>> {
        return firebaseService.getFavorites(userId)
    }
}
