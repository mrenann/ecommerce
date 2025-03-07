package br.mrenann.core.domain.model


enum class DiscountReason(
    val errorTitle: String,
    val errorMessage: String,
) {
    UNKNOWN_ERROR(
        "Unknown Error",
        "We couldn't determine the order status."
    ),
    COUPON_NOT_FOUND(
        "Coupon Not Found",
        "The coupon code you entered is invalid or does not exist."
    ),
    COUPON_EXPIRED(
        "Coupon Expired",
        "This coupon has expired and is no longer valid."
    ),
    MIN_PURCHASE_NOT_MET(
        "Minimum Purchase Not Met",
        "Your cart total does not meet the minimum purchase requirement for this coupon."
    ),
    COUPON_ALREADY_REDEEMED(
        "Coupon Already Redeemed",
        "This coupon has already been redeemed and cannot be used again."
    ),
    INVALID_DISCOUNT_TYPE(
        "Invalid Discount Type",
        "The discount type for this coupon is invalid."
    );

    fun toPair(): Pair<String, String> {
        return Pair(errorTitle, errorMessage)
    }
}

