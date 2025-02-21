package br.mrenann.core.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseService {
    private val db = FirebaseFirestore.getInstance()

    suspend fun addFavorite(userId: String, productId: String, productData: Map<String, Any>) {
        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(productId)
            .set(productData)
            .await()
    }

    suspend fun removeFavorite(userId: String, productId: String) {
        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(productId)
            .delete()
            .await()
    }

    suspend fun getFavorites(userId: String): List<Map<String, Any>> {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .await()

        return snapshot.documents.map { it.data ?: emptyMap() }
    }
}
