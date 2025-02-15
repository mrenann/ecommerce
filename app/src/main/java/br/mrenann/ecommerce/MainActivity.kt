package br.mrenann.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.mrenann.ecommerce.ui.theme.EcommerceTheme
import br.mrenann.main.presentation.MainScreen
import cafe.adriel.voyager.navigator.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EcommerceTheme {
                Navigator(MainScreen())
            }
        }
    }
}
