package br.mrenann.cart.data.usecase

import android.util.Log
import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.usecase.ApplyCouponUseCase
import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ApplyCouponUseCaseImpl(
    private val firestoreRepository: FavoritesFirestoreRepository, // Renamed for clarity
    private val cartRepository: CartRepository
) : ApplyCouponUseCase {
    override suspend fun invoke(params: ApplyCouponUseCase.Params): Flow<ApplyCouponUseCase.Result> {
        return flow {
            val coupon = firestoreRepository.getCoupon(params.code)
            if (coupon == null) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            val discountValue = coupon.discountValue.toDouble()
            val discountType = coupon.discountType
            val minPurchase = (coupon.minPurchase as? Number)?.toDouble() ?: 0.0
            val maxDiscount = (coupon.maxDiscount as? Number)?.toDouble() ?: Double.MAX_VALUE
            val expirationDateStr = coupon.expirationDate as? String ?: ""


            // Validate expiration date
            val currentDate = Calendar.getInstance().time
            val expiryDate =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(
                    expirationDateStr
                )
            if (expiryDate != null && expiryDate.before(currentDate)) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            // Validate minimum purchase
            if (params.subtotal < minPurchase) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            // Check if the user can redeem the coupon (using Firestore)
            val isRedeemed = firestoreRepository.redeemCoupon(params.userId, params.code)
            if (!isRedeemed) {
                emit(ApplyCouponUseCase.Result.Invalid)
                return@flow
            }

            // Calculate the discount amount (correctly, based on subtotal)
            val (discountPercentage, discountAmount, isFixedAmount) = when (discountType) {
                "percentage" -> {
                    val calculatedDiscount =
                        (params.subtotal * discountValue / 100).coerceAtMost(maxDiscount)
                    Triple(
                        discountValue,
                        calculatedDiscount,
                        false
                    ) // percentage, amount, not fixed
                }

                "fixed" -> {
                    Triple(
                        null,
                        discountValue.coerceAtMost(maxDiscount),
                        true
                    ) // null percentage, amount, fixed
                }

                else -> {
                    emit(ApplyCouponUseCase.Result.Invalid) // Unknown discount type
                    return@flow // Exit the flow
                }
            }
            // Apply the coupon to the Room database (using the CartRepository)
            cartRepository.addCoupon(params.code, discountPercentage, discountAmount, isFixedAmount)
            emit(ApplyCouponUseCase.Result.Success(coupon, discountAmount)) // Emit success

        }.catch { e ->  //Catch the exceptions
            Log.e("ApplyCouponUseCase", "Error applying coupon: ${e.message}", e)
            emit(ApplyCouponUseCase.Result.Invalid)
        }
    }
}

