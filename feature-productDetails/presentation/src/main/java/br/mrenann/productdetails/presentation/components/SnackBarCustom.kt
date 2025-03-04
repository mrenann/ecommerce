package br.mrenann.productdetails.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SnackBarCustom(
    snackbarHostState: SnackbarHostState,
    snackBarState: Boolean,
) {
    var containerColor by remember { mutableStateOf(Color.Red) }
    var contentColor by remember { mutableStateOf(Color.Black) }

    if (snackBarState) {
        contentColor = Color.White
        containerColor = Color(0xFF00A650)
    } else {
        contentColor = Color.White
        containerColor = Color(0xFF191919)
    }
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(6.dp),
        snackbar = {
            Snackbar(
                containerColor = containerColor,
                contentColor = contentColor,
            ) {

                Text(
                    text = (snackbarHostState.currentSnackbarData?.visuals?.message
                        ?: "").toString()
                )
            }
        }
    )
}