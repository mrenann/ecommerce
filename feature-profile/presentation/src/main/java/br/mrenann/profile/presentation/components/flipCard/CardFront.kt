package br.mrenann.profile.presentation.components.flipCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.R
import br.mrenann.core.domain.model.CardBrand
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Americanexpress
import compose.icons.simpleicons.Bandcamp
import compose.icons.simpleicons.Contactlesspayment
import compose.icons.simpleicons.Discover
import compose.icons.simpleicons.Ello
import compose.icons.simpleicons.Jcb
import compose.icons.simpleicons.Mastercard
import compose.icons.simpleicons.Visa

@Composable
fun CardFront(
    number: String,
    expiry: String,
    cardBrand: CardBrand
) {
    val auth = Firebase.auth
    val firestore = Firebase.firestore
    val userId = auth.currentUser?.uid
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    LocalFocusManager.current
    var isCardNumberFocused by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        if (userId != null) {
            firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userName = document.getString("name") ?: "Usuário"
                    }
                }
                .addOnFailureListener {
                    userName = "Erro ao carregar"
                }
        }
    }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFF7F33),
                        Color(0xFF735DFF),
                    )
                )
            )
            .drawBehind {
                drawCircle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF7F33).copy(alpha = 0.4f),
                            Color(0xFF735DFF).copy(alpha = 0.2f)
                        )
                    ),
                    center = this.size.center * 2f,
                    radius = this.size.width / 2f
                )
                drawCircle(
                    brush = Brush.linearGradient(
                        tileMode = TileMode.Mirror,
                        colors = listOf(
                            Color(0xFFFF7F33).copy(alpha = 0.4f),
                            Color(0xFF735DFF).copy(alpha = 0.2f)
                        )
                    ),
                    center = this.size.center.copy(y = this.size.height * 1.4f),
                    radius = this.size.width / 2.5f
                )
            }
            .heightIn(min = 185.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = when (cardBrand) {
                CardBrand.VISA -> SimpleIcons.Visa
                CardBrand.MASTERCARD -> SimpleIcons.Mastercard
                CardBrand.AMEX -> SimpleIcons.Americanexpress
                CardBrand.ELO -> SimpleIcons.Ello
                CardBrand.HIPERCARD -> SimpleIcons.Mastercard
                CardBrand.DINERS -> SimpleIcons.Mastercard
                CardBrand.DISCOVER -> SimpleIcons.Discover
                CardBrand.JCB -> SimpleIcons.Jcb
                CardBrand.UNKNOWN -> SimpleIcons.Bandcamp
            }
            Icon(
                tint = Color.White,
                imageVector = icon,
                contentDescription = "Search Icon"
            )
            Icon(
                tint = Color.White,
                imageVector = SimpleIcons.Contactlesspayment,
                contentDescription = "Search Icon"
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = formatCardNumber(number).text,
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = FontFamily(
                Font(R.font.atkinson_hyperlegible),
            )

        )
        Row(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .padding(bottom = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                NameDisplay(userName)
                Text(
                    text = expiry,
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }

    }
}


fun formatCardNumber(number: String): TextFieldValue {
    val formattedNumber = number.replace(" ", "").chunked(4).joinToString(" ")
    return TextFieldValue(
        text = formattedNumber,
        selection = TextRange(formattedNumber.length) // Set cursor to end
    )
}

@Composable
fun NameDisplay(userName: String) {
    val words = userName.trim().split("\\s+".toRegex())
    val displayName = words.take(2).joinToString(" ")

    Text(
        text = displayName,
        color = Color.White,
        fontSize = 17.sp
    )
}


