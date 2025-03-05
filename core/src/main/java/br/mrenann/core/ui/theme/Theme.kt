package br.mrenann.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFF00020A),
    onBackground = Color.White,
    onPrimary = Color.Red,
    primaryContainer = Color.Red,
    onPrimaryContainer = Color.Red,
    inversePrimary = Color.Red,
    onSecondary = Color.White,
    secondaryContainer = Color.Red,
    onSecondaryContainer = Color.Red,
    onTertiary = Color.Red,
    tertiaryContainer = Color.Red,
    onTertiaryContainer = Color.Red,
    surface = Color.Red,
    onSurface = Color.White,
    surfaceVariant = Color.Red,
    onSurfaceVariant = Color.White,
    surfaceTint = Color.Red,
    inverseSurface = Color.Red,
    inverseOnSurface = Color.Red,
    error = Color.Red,
    onError = Color.Red,
    errorContainer = Color.Red,
    onErrorContainer = Color.Red,
    outline = Color.Red,
    outlineVariant = Color.Red,
    scrim = Color.Black,
    surfaceBright = Color.Red,
    surfaceContainer = Color(0xFF07091C),
    surfaceContainerHigh = Color(0xFF07091C),
    surfaceContainerHighest = Color(0xFF07091C),
    surfaceContainerLow = Color(0xFF07091C),
    surfaceContainerLowest = Color(0xFF07091C),
    surfaceDim = Color(0xFF07091C),
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFF5F8FE),
    onBackground = Color.Black,
    onPrimary = Color.Black,
    primaryContainer = Color.Yellow,
    onPrimaryContainer = Color.Black,
    inversePrimary = Color.Yellow,
    onSecondary = Color.Black,
    secondaryContainer = Color.Yellow,
    onSecondaryContainer = Color.Black,
    onTertiary = Color.Black,
    tertiaryContainer = Color.Yellow,
    onTertiaryContainer = Color.Black,
    surface = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = Color.Yellow,
    onSurfaceVariant = Color.Black,
    surfaceTint = Color.Black,
    inverseSurface = Color.Yellow,
    inverseOnSurface = Color.Yellow,
    error = Color.Yellow,
    onError = Color.Yellow,
    errorContainer = Color.Yellow,
    onErrorContainer = Color.Yellow,
    outline = Color(0xFFF2F2F2),
    outlineVariant = Color(0xFFF2F2F2),
    scrim = Color(0xFFF2F2F2),
    surfaceBright = Color.Blue,
    surfaceContainer = Color.White,
    surfaceContainerHigh = Color.White,
    surfaceContainerHighest = Color.White,
    surfaceContainerLow = Color.White,
    surfaceContainerLowest = Color.White,
    surfaceDim = Color.White,

    )

@Composable
fun EcommerceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}