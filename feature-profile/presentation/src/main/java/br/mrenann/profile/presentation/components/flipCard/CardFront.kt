package br.mrenann.profile.presentation.components.flipCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Archive
import compose.icons.evaicons.fill.Email

@Composable
fun CardFront(finish: (CardFace, CardData) -> Unit) {
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
                        Color(0xFF171717),
                        Color(0xFF3B3B3B),
                    )
                )
            )
            .drawBehind {
                drawCircle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF171717).copy(alpha = 0.4f),
                            Color(0xFF3B3B3B).copy(alpha = 0.2f)
                        )
                    ),
                    center = this.size.center * 2f,
                    radius = this.size.width / 2f
                )
                drawCircle(
                    brush = Brush.linearGradient(
                        tileMode = TileMode.Mirror,
                        colors = listOf(
                            Color(0xFF1E1E1F).copy(alpha = 0.4f),
                            Color(0xFF3B3B3B).copy(alpha = 0.2f)
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
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                tint = Color.White,

                imageVector = EvaIcons.Fill.Archive,
                contentDescription = "Search Icon"
            )
            Icon(
                tint = Color.White,
                imageVector = EvaIcons.Fill.Email,
                contentDescription = "Search Icon"
            )
        }
        OutlinedTextField(
            singleLine = true,
            value = formatCardNumber(cardNumber),
            onValueChange = { textFieldValue -> // Receive TextFieldValue
                val unformatted = textFieldValue.text.replace(" ", "")
                if (unformatted.length <= 16) {
                    cardNumber = unformatted
                }
            },
            placeholder = {
                Text(
                    text = "1234 5678 9012 3456",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.atkinson_hyperlegible),
                    ),
                    textAlign = TextAlign.Center,


                    )
            },

            shape = OutlinedTextFieldDefaults.shape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            visualTransformation = DigitsAndSpacesTransformation(), // Key change!
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.atkinson_hyperlegible)),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(30.dp, 70.dp)
                .focusRequester(focusRequester1)
                .onFocusChanged {
                    isCardNumberFocused = it.isFocused
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if (cardNumber.length == 16) {
                        focusRequester2.requestFocus()
                    }
                }
            )
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .padding(bottom = 8.dp)
        ) {
            Row {
                NameDisplay(userName)
                BasicTextField(
                    value = expiryDate,
                    onValueChange = {
                        if (it.length <= 5) {
                            expiryDate = it.replace(Regex("[^0-9/]"), "") // Only numbers and /
                                .replace(Regex("^([0-9]{2})([0-9]{2})"), "$1/$2") // Format as MM/YY
                        }

                        if (expiryDate.length == 5 && cardNumber.length == 16) {
                            val cardData =
                                CardData(cardNumber, expiryDate, "") // Create CardData object
                            finish(CardFace.Front, cardData) // Pass CardData to finish
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    enabled = cardNumber.length >= 16, // Enable when card number is complete
                    textStyle = TextStyle(
                        color = Color.White
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .padding(0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            innerTextField()
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester2)
                )
            }
        }

    }
}


class DigitsAndSpacesTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Allow only digits and spaces
        val filteredText = text.text.filter { it.isDigit() || it.isWhitespace() }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = 0
                for (i in 0 until offset) {
                    if (text.text[i].isDigit() || text.text[i].isWhitespace()) {
                        transformedOffset++
                    }
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = 0
                var transformedOffset = 0
                while (transformedOffset < offset && originalOffset < text.text.length) {
                    if (text.text[originalOffset].isDigit() || text.text[originalOffset].isWhitespace()) {
                        transformedOffset++
                    }
                    originalOffset++
                }
                return originalOffset
            }
        }
        return TransformedText(AnnotatedString(filteredText), offsetMapping)
    }
}

fun formatCardNumber(number: String): TextFieldValue {  // Return TextFieldValue
    val formattedNumber = number.replace(" ", "").chunked(4).joinToString(" ")
    return TextFieldValue(
        text = formattedNumber,
        selection = TextRange(formattedNumber.length) // Set cursor to end
    )
}

@Composable
fun NameDisplay(userName: String) {
    val words = userName.trim().split("\\s+".toRegex()) // Splits by multiple spaces
    val displayName = words.take(2).joinToString(" ") // Takes first two words safely

    Text(
        text = displayName,
        color = Color.White,
        fontSize = 17.sp
    )
}


