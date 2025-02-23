package br.mrenann.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.mrenann.profile.presentation.components.flipCard.Card
import cafe.adriel.voyager.core.screen.Screen

class CardScreen() : Screen {
    @Composable
    override fun Content() {

        Column {
            Text("aas")
            Text("aas")
            Text("aas")
            Text("aas")
            Card()
        }
    }
}