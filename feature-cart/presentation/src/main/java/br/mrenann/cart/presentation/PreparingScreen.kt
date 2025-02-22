package br.mrenann.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay

class PreparingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // Navigate to AddressScreen after 3 seconds
        LaunchedEffect(Unit) {
            delay(3000) // 3 seconds delay
            navigator.replace(AddressScreen()) // Replace with your actual AddressScreen
        }

        PreparingScreenContent()
    }
}

@Composable
fun PreparingScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color(0xFF00C236))
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Preparing everything for you...",
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview
fun PreparingScreenPreview() {
    PreparingScreenContent()
}
