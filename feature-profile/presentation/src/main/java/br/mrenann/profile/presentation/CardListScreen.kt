package br.mrenann.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Trash
import kotlinx.coroutines.tasks.await

data class Card(
    val cardNumber: String = "",
    val cvv: String = "",
    val expiryDate: String = ""
)


class CardListScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var cards: List<Card> = emptyList()
        LaunchedEffect(true) {
            val db = Firebase.firestore
            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) { // Check if cardData is available
                val cardRef =
                    db.collection("users").document(userId).collection("cards").get().await()
                cards = cardRef.documents.mapNotNull {
                    it.toObject(Card::class.java)
                }
            } else {
                // Handle error: User not logged in or cardData not available
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F8FE))
                    .padding(innerPadding)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            tint = Color.Black,
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = "Cards",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            tint = Color.Black,
                            imageVector = EvaIcons.Outline.Trash,
                            contentDescription = "Clear Cart"
                        )
                    }
                }

                Column {
                    Text("${cards}")
                }


            }
        }
    }
}