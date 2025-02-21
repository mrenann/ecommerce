package br.mrenann.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class FavoriteItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String? = null
)