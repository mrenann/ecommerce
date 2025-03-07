package br.mrenann.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.mrenann.core.data.local.entity.AppliedCouponEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppliedCouponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppliedCoupon(appliedCoupon: AppliedCouponEntity)

    @Query("SELECT * FROM applied_coupon WHERE id = 1")
    fun getAppliedCoupon(): Flow<AppliedCouponEntity?>

    @Query("DELETE FROM applied_coupon")
    suspend fun clearAppliedCoupon()
}