package br.mrenann.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.mrenann.core.data.local.dao.CartDao
import br.mrenann.core.data.local.dao.FavoritesDao
import br.mrenann.core.data.local.entity.CartItemEntity
import br.mrenann.core.data.local.entity.FavoriteItemEntity

@Database(
    entities = [CartItemEntity::class, FavoriteItemEntity::class],
    version = 5,
    exportSchema = false
)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun favoritesDao(): FavoritesDao
}