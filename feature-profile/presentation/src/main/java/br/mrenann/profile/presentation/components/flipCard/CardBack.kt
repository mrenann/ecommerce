package br.mrenann.profile.presentation.components.flipCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardBack(finish: (CardFace, String) -> Unit) { // Add finish parameter
    var code by remember { mutableStateOf("") }
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
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier)

            Column(horizontalAlignment = Alignment.End) {
//                Image(
//                    modifier = Modifier.size(36.dp),
//                    painter = painterResource(SharedRes.image.ufcalogo),
//                    colorFilter = ColorFilter.tint(Color.White),
//                    contentDescription = null
//                )

            }

        }
        Spacer(modifier = Modifier.height(64.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            BasicTextField(
                value = code,
                onValueChange = {
                    if (it.length <= 3) code = it.filter { it.isDigit() }

                    if (code.length == 3) {
                        finish(CardFace.Back, code) // Call finish with CVV
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
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
                    .background(Color.White)
                    .padding(6.dp)
                    .width(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}
