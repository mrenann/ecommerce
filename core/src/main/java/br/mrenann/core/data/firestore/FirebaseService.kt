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

    suspend fun getCoupon(code: String): Map<String, Any>? {
        val doc = db.collection("coupons").document(code).get().await()
        return if (doc.exists()) doc.data else null
    }

    suspend fun redeemCoupon(userId: String, code: String): Boolean {
        val couponRef = db.collection("coupons").document(code)
        val snapshot = couponRef.get().await()

        if (!snapshot.exists()) return false

        val data = snapshot.data ?: return false
        val maxRedemptions = (data["maxRedemptions"] as? Number)?.toInt() ?: Int.MAX_VALUE
        val redeemedBy = (data["redeemedBy"] as? List<*>)?.map { it.toString() } ?: emptyList()

        if (redeemedBy.contains(userId) || redeemedBy.size >= maxRedemptions) {
            return false // Coupon already used or exceeded max redemptions
        }

        couponRef.update("redeemedBy", redeemedBy + userId).await()
        return true
    }
}
