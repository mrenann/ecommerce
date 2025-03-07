package br.mrenann.profile.presentation.components.flipCard

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.mrenann.core.domain.model.CardBrand

data class CardData(val cardNumber: String, val expiryDate: String, val cvv: String)

@Composable
fun Card(
    state: CardFace = CardFace.Front,
    number: String,
    cvv: String,
    expiry: String,
    cardBrand: CardBrand
) {

    var cardData by remember { mutableStateOf<CardData?>(null) } // Store card data

    Log.i("state", "satte state ${state}")
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        FlipCardItem(
            modifier = Modifier
                .widthIn(max = 500.dp)
                .heightIn(min = 200.dp)
                .padding(vertical = 36.dp, horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),

            cardFace = state,
            onClick = {

            },
            front = {
                CardFront(
                    cardBrand = cardBrand,
                    number = number,
                    expiry = expiry,
                    finish = { cardFace, data -> // Update lambda to receive CardData

//                        state = cardFace.flip()
                    })
            },
            back = {
                CardBack(
                    cvv = cvv,
                    finish = { cardFace, code ->
//                    cvv = code // Store CVV from CardBack
//                    // Combine cardData and cvv and send to Firestore
//                    val db = Firebase.firestore
//                    val userId = Firebase.auth.currentUser?.uid
//
//                    if (userId != null && cardData != null) { // Check if cardData is available
//                        val cardRef =
//                            db.collection("users").document(userId).collection("cards").document()
//                        val completeCardData = cardData!!.copy(cvv = cvv) // Add cvv to cardData
//                        cardRef.set(completeCardData)
//                            .addOnSuccessListener {
//                                // Handle success
////                                state = cardFace.flip()
//                            }
//                            .addOnFailureListener {
//                                // Handle error
//                            }
//                    } else {
//                        // Handle error: User not logged in or cardData not available
//                    }

                    })
            }
        )
    }
}