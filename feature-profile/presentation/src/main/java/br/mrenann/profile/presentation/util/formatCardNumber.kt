package br.mrenann.profile.presentation.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun String.formatCardNumber(): TextFieldValue {  // Return TextFieldValue
    val formattedNumber = this.replace(" ", "").chunked(4).joinToString(" ")
    return TextFieldValue(
        text = formattedNumber,
        selection = TextRange(formattedNumber.length) // Set cursor to end
    )
}