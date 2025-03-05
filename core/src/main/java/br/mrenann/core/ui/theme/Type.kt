package br.mrenann.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import br.mrenann.core.R


val sfprodisplayFontFamily = FontFamily(
    Font(R.font.sfprodisplay_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.sfprodisplay_bold, FontWeight.Bold),
    Font(R.font.sfprodisplay_heavy_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.sfprodisplay_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.sfprodisplay_medium, FontWeight.Medium),
    Font(R.font.sfprodisplay_regular, FontWeight.Normal),
    Font(R.font.sfprodisplay_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.sfprodisplay_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.sfprodisplay_ultralight_italic, FontWeight.ExtraLight, FontStyle.Italic)
)

val novaSquareFamily = FontFamily(
    Font(R.font.novasquare, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = sfprodisplayFontFamily,
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


