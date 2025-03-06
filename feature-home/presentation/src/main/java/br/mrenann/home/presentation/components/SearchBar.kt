package br.mrenann.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Search

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit // Callback to handle search
) {
    // Local software keyboard controller to hide keyboard after search
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        maxLines = 1,
        onValueChange = onQueryChange,
        singleLine = true,
        placeholder = { Text("Search") },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    if (query.length > 3) onSearch(query)
                },
                imageVector = EvaIcons.Outline.Search,
                contentDescription = "Search Icon"
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done // Sets the action to "Done" on the keyboard
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (query.length > 3) onSearch(query) // Trigger the search when "Done" is pressed
                keyboardController?.hide() // Hide the keyboard after the search
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}
