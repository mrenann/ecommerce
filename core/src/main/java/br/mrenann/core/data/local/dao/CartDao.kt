package br.mrenann.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.mrenann.core.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(item: CartItemEntity)

    @Delete
    fun removeFromCart(item: CartItemEntity)

    @Query("DELETE FROM cart_items")
    fun clearCart()

    @Query("SELECT * FROM cart_items WHERE productId = :id")
    suspend fun inList(id: String): CartItemEntity?

    @Query("UPDATE cart_items SET quantity = quantity + 1 WHERE productId = :id")
    suspend fun increaseQuantity(id: String)

    @Query("UPDATE cart_items SET quantity = quantity - 1 WHERE productId = :id AND quantity > 1")
    suspend fun decreaseQuantity(id: String)

    @Query("UPDATE cart_items SET discount = :discount, couponCode = :coupon WHERE productId = :id")
    suspend fun applyCoupon(id: String, discount: Double, coupon: String)
}
