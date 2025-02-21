package br.mrenann.cart.data.usecase

import android.util.Log
import br.mrenann.cart.domain.usecase.ApplyCouponUseCase
import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ApplyCouponUseCaseImpl(
    private val repository: FavoritesFirestoreRepository
) : ApplyCouponUseCase {
    override suspend fun invoke(params: ApplyCouponUseCase.Params): Flow<ApplyCouponUseCase.Result> {
        return flow<ApplyCouponUseCase.Result> {
            val coupon = repository.getCoupon(params.code)
            if (coupon == null) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            val discountValue = (coupon["discountValue"] as? Number)?.toDouble() ?: 0.0
            val discountType = coupon["discountType"] as? String ?: "percentage"
            val minPurchase = (coupon["minPurchase"] as? Number)?.toDouble() ?: 0.0
            val maxDiscount = (coupon["maxDiscount"] as? Number)?.toDouble() ?: Double.MAX_VALUE
            val expirationDateStr = coupon["expirationDate"] as? String ?: ""

            // Validate expiry date
            val currentDate = Calendar.getInstance().time
            val expiryDate =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(
                    expirationDateStr
                )
            if (expiryDate != null && expiryDate.before(currentDate)) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            // Validate min purchase
            if (params.subtotal < minPurchase) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            // Validate redemption
            val isRedeemed = repository.redeemCoupon(params.userId, params.code)
            if (!isRedeemed) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            // Calculate discount
            val discountAmount = when (discountType) {
                "percentage" -> (params.subtotal * discountValue / 100).coerceAtMost(maxDiscount)
                "fixed" -> discountValue.coerceAtMost(maxDiscount)
                else -> 0.0
            }

            Log.i("COUPON", "${params.subtotal} -> $discountAmount")

            emit(ApplyCouponUseCase.Result.Success(discountAmount))
        }
    }
}
