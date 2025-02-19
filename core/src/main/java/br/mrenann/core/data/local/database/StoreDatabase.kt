package br.mrenann.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.mrenann.core.data.local.dao.CartDao
import br.mrenann.core.data.local.entity.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}