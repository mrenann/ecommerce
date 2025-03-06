package br.mrenann.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.profile.presentation.components.flipCard.Card
import br.mrenann.profile.presentation.components.flipCard.CardFace
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Calendar
import compose.icons.evaicons.fill.CreditCard
import compose.icons.evaicons.fill.Lock
import compose.icons.evaicons.outline.ChevronLeft

class CardScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var number by remember { mutableStateOf("") }
        var cvv by remember { mutableStateOf("") }
        var expiry by remember { mutableStateOf("") }
        var state: CardFace by remember { mutableStateOf(CardFace.Front) }

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
                        text = "Card",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                }

                Card(
                    state = state,
                    number = number,
                    cvv = cvv,
                    expiry = expiry
                )

                Column(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        singleLine = true,
                        value = formatCardNumber(number),
                        minLines = 1,
                        maxLines = 1,
                        onValueChange = { textFieldValue -> // Receive TextFieldValue
                            val unformatted = textFieldValue.text.replace(" ", "")
                            if (unformatted.length <= 16) {
                                number = unformatted
                            }
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = EvaIcons.Fill.CreditCard,
                                contentDescription = "Credit Icon"
                            )
                        },
                        placeholder = { Text("Card number") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState: FocusState ->
                                if (focusState.isFocused) {
                                    state = CardFace.Front
                                }
                            },
                        visualTransformation = DigitsAndSpacesTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = expiry,
                            onValueChange = {
                                if (it.length <= 5) {
                                    expiry = it.replace(Regex("[^0-9/]"), "") // Only numbers and /
                                        .replace(
                                            Regex("^([0-9]{2})([0-9]{2})"),
                                            "$1/$2"
                                        ) // Format as MM/YY
                                }

                            },
                            minLines = 1,
                            maxLines = 1,
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = EvaIcons.Fill.Calendar,
                                    contentDescription = "Search Icon"
                                )
                            },
                            placeholder = { Text("Expire date (MM/YY)") },
                            modifier = Modifier
                                .weight(1F)
                                .onFocusChanged { focusState: FocusState ->
                                    if (focusState.isFocused) {
                                        state = CardFace.Front
                                    }
                                },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        )

                        OutlinedTextField(
                            value = cvv,
                            minLines = 1,
                            maxLines = 1,
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = EvaIcons.Fill.Lock,
                                    contentDescription = "Search Icon"
                                )
                            },
                            onValueChange = {
                                if (it.length <= 3) {
                                    cvv = it.filter { char -> char.isDigit() }
                                }
                            },
                            placeholder = { Text("CVV") },
                            modifier = Modifier
                                .weight(1F)
                                .onFocusChanged { focusState: FocusState ->
                                    if (focusState.isFocused) {
                                        state = CardFace.Back
                                    }
                                },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        )
                    }
                }
            }
        }
    }

    fun formatCardNumber(number: String): TextFieldValue {  // Return TextFieldValue
        val formattedNumber = number.replace(" ", "").chunked(4).joinToString(" ")
        return TextFieldValue(
            text = formattedNumber,
            selection = TextRange(formattedNumber.length) // Set cursor to end
        )
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
}
