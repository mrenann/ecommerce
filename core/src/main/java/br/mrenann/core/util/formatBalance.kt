package br.mrenann.core.util

fun Int.formatBalance(
    withDecimals: Boolean = true,
    currency: String = "R$",
    showCurrency: Boolean = true
): String {
    val formattedValue = if (withDecimals) {
        String.format("%,d", this) + ",00"
    } else {
        String.format("%,d", this)
    }

    return if (showCurrency) "$currency $formattedValue" else formattedValue
}

fun Double.formatBalance(
    withDecimals: Boolean = true,
    currency: String = "R$",
    showCurrency: Boolean = true
): String {
    val formattedValue = if (withDecimals) {
        String.format("%,.2f", this)
    } else {
        String.format("%,.0f", this)
    }

    return if (showCurrency) "$currency $formattedValue" else formattedValue
}
