package br.mrenann.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.mrenann.auth.presentation.LoginScreen
import br.mrenann.ecommerce.ui.theme.EcommerceTheme
import br.mrenann.main.presentation.MainScreen
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val auth = Firebase.auth
        val initialScreen = if (auth.currentUser != null) MainScreen() else LoginScreen()
        setContent {
            EcommerceTheme {
                Navigator(initialScreen)
            }
        }
    }
}
