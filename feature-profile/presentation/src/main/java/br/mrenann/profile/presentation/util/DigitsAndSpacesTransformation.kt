package br.mrenann.profile.presentation.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DigitsAndSpacesTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Allow only digits and spaces
        val filteredText = text.text.filter { it.isDigit() || it.isWhitespace() }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = 0
                for (i in 0 until offset) {
                    if (text.text[i].isDigit() || text.text[i].isWhitespace()) {
                        transformedOffset++
                    }
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = 0
                var transformedOffset = 0
                while (transformedOffset < offset && originalOffset < text.text.length) {
                    if (text.text[originalOffset].isDigit() || text.text[originalOffset].isWhitespace()) {
                        transformedOffset++
                    }
                    originalOffset++
                }
                return originalOffset
            }
        }
        return TransformedText(AnnotatedString(filteredText), offsetMapping)
    }
}