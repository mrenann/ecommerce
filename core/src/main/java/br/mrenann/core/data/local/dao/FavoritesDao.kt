package br.mrenann.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.mrenann.core.data.local.entity.FavoriteItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getFavoritesItems(): Flow<List<FavoriteItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(item: FavoriteItemEntity)

    @Delete
    fun removeFromFavorites(item: FavoriteItemEntity)

    @Query("DELETE FROM favorites")
    fun clearFavorites()

    @Query("SELECT * FROM favorites WHERE productId = :id")
    suspend fun inList(id: String): FavoriteItemEntity?
}