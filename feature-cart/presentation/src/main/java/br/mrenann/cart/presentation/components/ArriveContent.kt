package br.mrenann.cart.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Car
import compose.icons.evaicons.fill.Flash
import compose.icons.evaicons.outline.Car
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Pin
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ArriveContent(
    navigatePop: () -> Boolean,
    goToNext: () -> Unit,
    position: String
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigatePop() }) {
                Icon(
                    tint = Color.Black,
                    imageVector = EvaIcons.Outline.ChevronLeft,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Choose delivery date",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    imageVector = EvaIcons.Outline.Pin,
                    contentDescription = "Search Icon"
                )
                Text(text = "To $position")

            }


        }

        Column(
            modifier = Modifier.weight(1F)
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            DeliverySelectionCard()

        }

        Column(
            modifier = Modifier.background(Color.White).padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Delivery",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = EvaIcons.Outline.Car,
                        contentDescription = "Search Icon",
                        tint = Color(0xFF098D19)
                    )
                    Spacer(modifier = Modifier.size(3.dp))
                    Text(text = "Free", color = Color(0xFF098D19))
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    goToNext()
                }
            ) {
                Text("Continue")
            }
        }

    }

}


@Composable
@Preview
fun ArriveContentPreview() {
    ArriveContent({ false }, {}, "Residence")
}