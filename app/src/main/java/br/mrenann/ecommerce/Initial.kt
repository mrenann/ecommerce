package br.mrenann.ecommerce

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object Initial : Screen {
    @Composable
    override fun Content() {
        Text("ECOMMERCE")
    }

}