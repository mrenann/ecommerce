package br.mrenann.profile.presentation.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import br.mrenann.profile.presentation.components.CardItem
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Plus
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.abs
import kotlin.math.roundToInt

data class Card(
    val cardNumber: String = "",
    val cvv: String = "",
    val expiryDate: String = "",
    val type: String = "",
    val id: String = "" // Add an ID field
)

class CardListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var cards by remember { mutableStateOf<List<Card>>(emptyList()) }
        var showDialog by remember { mutableStateOf(false) }
        var draggedCardIndex by remember { mutableStateOf(-1) }
        val cardOffsets = remember { mutableStateMapOf<Int, Float>() }
        val coroutineScope = rememberCoroutineScope()
        val strings = LocalStrings.current.profileTab.myCards

        LaunchedEffect(true) {
            val db = Firebase.firestore
            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) {
                val cardRef =
                    db.collection("users").document(userId).collection("cards").get().await()
                // Use .map instead of mapNotNull and set the ID
                cards = cardRef.documents.map {
                    val card = it.toObject(Card::class.java)
                    card?.copy(id = it.id) ?: Card() // Important: Handle null and set ID
                }
                Log.i("Firestore", "Fetched Cards: $cards")
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Header (same as before)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1F),
                        text = strings.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                }

                // Card List
                Column(
                    modifier = Modifier.height(195.dp),
                ) {
                    if (cards.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            itemsIndexed(cards) { index, card ->
                                val animatedOffsetY by animateFloatAsState(
                                    targetValue = cardOffsets[index] ?: 0f,
                                    label = ""
                                )

                                CardItem(
                                    card = card,
                                    index = index,
                                    modifier = Modifier
                                        .offset { IntOffset(0, animatedOffsetY.roundToInt()) }
                                        .zIndex(if (cardOffsets[index] != 0f) 1f else 0f)
                                        .pointerInput(Unit) {
                                            detectDragGestures(
                                                onDragStart = {
                                                    cardOffsets[index] = 0f
                                                    draggedCardIndex = index
                                                },
                                                onDrag = { change, dragAmount ->
                                                    if (abs(dragAmount.y) > 0) {
                                                        cardOffsets[index] =
                                                            (cardOffsets[index]
                                                                ?: 0f) + dragAmount.y
                                                        change.consume()
                                                    }
                                                },
                                                onDragEnd = {
                                                    coroutineScope.launch {
                                                        if ((cardOffsets[index] ?: 0f) < -120) {
                                                            showDialog = true
                                                        } else {
                                                            draggedCardIndex = -1
                                                        }
                                                        cardOffsets[index] = 0f
                                                    }
                                                },
                                                onDragCancel = {
                                                    coroutineScope.launch {
                                                        cardOffsets[index] = 0f
                                                        draggedCardIndex = -1
                                                    }
                                                }
                                            )
                                        }
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(185.dp)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = strings.noCards,
                                textAlign = TextAlign.Center,
                            )
                        }

                    }
                }

                if (cards.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp),
                        text = strings.toRemove
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconButton(
                        onClick = {
                            navigator.push(CardScreen())
                        },
                        colors = IconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            imageVector = EvaIcons.Outline.Plus,
                            contentDescription = "Localized description",
                        )
                    }
                }


                // Dialog - Now with Firebase deletion
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                            draggedCardIndex = -1

                        },
                        title = { Text(strings.deleteCard) }, // More specific title
                        text = {
                            Text(
                                strings.sureToRemove(
                                    cards.getOrNull(
                                        draggedCardIndex
                                    )?.cardNumber?.takeLast(4) ?: "N/A"
                                )
                            )
                        },
                        confirmButton = {
                            Button(onClick = {
                                coroutineScope.launch { // Use coroutine for deletion
                                    showDialog = false
                                    if (draggedCardIndex != -1) {
                                        val cardToDelete = cards.getOrNull(draggedCardIndex)
                                        cardToDelete?.id?.let { cardId ->  // Use the ID
                                            val db = Firebase.firestore
                                            val userId = Firebase.auth.currentUser?.uid
                                            if (userId != null) {
                                                try {
                                                    db.collection("users").document(userId)
                                                        .collection("cards").document(cardId)
                                                        .delete()
                                                        .await() // Await the deletion

                                                    // Update local list *after* successful deletion
                                                    val mutableCards = cards.toMutableList()
                                                    mutableCards.removeAt(draggedCardIndex)
                                                    cards = mutableCards.toList()
                                                } catch (e: Exception) {
                                                    Log.e("Firestore", "Error deleting card", e)
                                                    // Handle the error (e.g., show a Toast)
                                                }
                                            }
                                        }
                                        draggedCardIndex = -1 // Reset after deletion
                                    }
                                }
                            }) {
                                Text(strings.delete)
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDialog = false; draggedCardIndex = -1 }) {
                                Text(strings.cancel)
                            }
                        }
                    )
                }
            }
        }
    }


}