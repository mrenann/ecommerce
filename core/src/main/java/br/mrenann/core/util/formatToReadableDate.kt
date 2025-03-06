package br.mrenann.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToReadableDate(): String {
    val formatter = SimpleDateFormat("EEEE, MMMM d, HH:mm", Locale.getDefault())
    return formatter.format(this).split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() }
    }
}
