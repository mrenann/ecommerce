package br.mrenann.profile.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.mrenann.core.domain.model.Order
import cafe.adriel.voyager.core.screen.Screen

data class OrderScreen(
    val order: Order
) : Screen {
    @Composable
    override fun Content() {
        Text(text = "Order Details")
    }
}