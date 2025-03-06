package br.mrenann.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.mrenann.auth.presentation.LoginScreen
import br.mrenann.core.ui.strings.Strings
import br.mrenann.core.ui.theme.EcommerceTheme
import br.mrenann.main.presentation.MainScreen
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.rememberStrings
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

val LocalLyricist = staticCompositionLocalOf<Lyricist<Strings>> {
    error("Lyricist not provided")
}

class MainActivity : ComponentActivity() {
    private lateinit var lyricist: Lyricist<Strings>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        val auth = Firebase.auth
        val initialScreen = if (auth.currentUser != null) MainScreen() else LoginScreen()
        setContent {
            lyricist = rememberStrings()

            CompositionLocalProvider(LocalLyricist provides lyricist) {
                EcommerceTheme {
                    Navigator(initialScreen)
                }
            }
        }
    }
}
