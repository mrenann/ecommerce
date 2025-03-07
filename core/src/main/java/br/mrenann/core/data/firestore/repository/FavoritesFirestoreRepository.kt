package br.mrenann.core.data.firestore.repository

import br.mrenann.core.data.firestore.FirebaseService
import br.mrenann.core.domain.model.Discount

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

    suspend fun getCoupon(code: String): Discount? {
        return firebaseService.getCoupon(code)
    }

    suspend fun redeemCoupon(userId: String, code: String): Boolean {
        return firebaseService.redeemCoupon(userId, code)
    }
}
