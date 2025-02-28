package br.mrenann.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val discount: Double = 0.0,
    val couponCode: String? = null, // New field to store the coupon code
    val imageUrl: String? = null
) {
    fun totalPrice(): Double {
        return (price * quantity) - discount
    }
}

