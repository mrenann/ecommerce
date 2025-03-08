package br.mrenann.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import cafe.adriel.lyricist.LocalStrings
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Car
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Pin

@Composable
fun ArriveContent(
    navigatePop: () -> Boolean,
    goToNext: () -> Unit,
    position: String
) {
    val strings = LocalStrings.current.cartScreen
    val positionTranslated = if(position == "Residence") strings.residence else strings.agency
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigatePop() }) {
                Icon(
                    imageVector = EvaIcons.Outline.ChevronLeft,
                    contentDescription = "Back"
                )
            }
            Text(
                text = strings.chooseDeliveryDate,
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
                Text(text = strings.toPosition(positionTranslated))

            }


        }

        Column(
            modifier = Modifier.weight(1F)
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            DeliverySelectionCard()

        }

        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.delivery,
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
                    Text(text = strings.free, color = Color(0xFF098D19))
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    goToNext()
                }
            ) {
                Text(strings.continueBtn)
            }
        }

    }

}


@Composable
@Preview
fun ArriveContentPreview() {
    ArriveContent({ false }, {}, "Residence")
}