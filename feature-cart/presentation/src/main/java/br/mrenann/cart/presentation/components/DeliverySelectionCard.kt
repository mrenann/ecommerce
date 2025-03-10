package br.mrenann.cart.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.LocalStrings
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Flash
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DeliverySelectionCard() {
    val options = listOf(3, 4, 6) // Days ahead
    val today = LocalDate.now()
    val strings = LocalStrings.current.cartScreen
    var selectedOption by remember { mutableStateOf(options.first()) }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.oneDelivery,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = EvaIcons.Fill.Flash,
                            contentDescription = "Search Icon",
                            tint = Color(0xFF098D19)
                        )
                        Text(text = strings.full, color = Color(0xFF098D19))
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            // Delivery Options
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp)
            ) {
                options.forEach { daysAhead ->
                    val deliveryDate = today.plusDays(daysAhead.toLong())
                    val dayOfWeek =
                        deliveryDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { selectedOption = daysAhead },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == daysAhead,
                            onClick = { selectedOption = daysAhead }
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Row(
                            modifier = Modifier.weight(1F),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = strings.daysAhedToDelivery(daysAhead, dayOfWeek))
                            Text(text = strings.free, color = Color(0xFF098D19))
                        }
                    }
                }
            }
        }
    }
}
