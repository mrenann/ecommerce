package br.mrenann.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.mrenann.core.data.local.dao.AppliedCouponDao
import br.mrenann.core.data.local.dao.CartDao
import br.mrenann.core.data.local.dao.FavoritesDao
import br.mrenann.core.data.local.entity.AppliedCouponEntity
import br.mrenann.core.data.local.entity.CartItemEntity
import br.mrenann.core.data.local.entity.FavoriteItemEntity

@Database(
    entities = [
        CartItemEntity::class,
        FavoriteItemEntity::class,
        AppliedCouponEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun appliedCouponDao(): AppliedCouponDao

}