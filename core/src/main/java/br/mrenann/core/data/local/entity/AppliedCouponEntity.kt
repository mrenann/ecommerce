package br.mrenann.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "applied_coupon")
data class AppliedCouponEntity(
    @PrimaryKey val id: Int = 1, // Still using a fixed ID
    @ColumnInfo(name = "coupon_code") val couponCode: String?, // Code from the API
    @ColumnInfo(name = "discount_percentage") val discountPercentage: Double?, // From API, could be null
    @ColumnInfo(name = "discount_amount") val discountAmount: Double?,     //From API, could be null
    @ColumnInfo(name = "is_fixed_amount") val isFixedAmount: Boolean?  // From API
    // ... other coupon details from the API ...
)