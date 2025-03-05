package br.mrenann.cart.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.Card
import br.mrenann.cart.presentation.R
import br.mrenann.core.util.formatBalance
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronRight

@Composable
fun ChoosePaymentContent(
    goToNext: (String, Card?) -> Unit,
    cards: List<Card>,
    value: Double,
) {
    Column {
        LazyColumn(
            modifier = Modifier.weight(1F),
        ) {
            item {
                PaymentOption(
                    icon = painterResource(R.drawable.ic_pix), // Replace
                    title = "Pix",
                    subtitle = "Aprovação imediata",
                    cashback = "",
                    recommended = true,
                    click = goToNext
                )
            }


            items(cards.size) { index ->
                val card = cards[index]
                BankPaymentOption(card, goToNext)
            }


        }

        TotalAmountSection(value.formatBalance())
    }

}


@Composable
fun PaymentOption(
    icon: Painter,
    title: String,
    subtitle: String,
    cashback: String,
    recommended: Boolean = false,
    isNew: Boolean = false,
    click: (String, Card?) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
            .clickable { click("pix", null) }

            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Important for spacing
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F0FE)), // Light blue circle background
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon,
                    contentDescription = null, // Provide a meaningful description
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, fontWeight = FontWeight.Bold)
                    if (isNew) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "NOVO",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    Color.Blue,
                                    shape = RoundedCornerShape(2.dp)
                                )  // Small blue tag
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
                Text(text = cashback, fontSize = 12.sp, color = Color(0xFF008000)) // Green color
            }
        }


        if (recommended) {
            Text(
                text = "RECOMENDADO",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        Color.Blue,
                        shape = RoundedCornerShape(4.dp)
                    ) // Rounded blue background
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        } else {

            Icon(
                imageVector = EvaIcons.Outline.ChevronRight,
                contentDescription = "See more",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun BankPaymentOption(
    card: Card,
    click: (String, Card?) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .fillMaxWidth()
            .clickable { click("card", card) }
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Card", fontWeight = FontWeight.Bold)
                Text(
                    text = "**** ${card.cardNumber.takeLast(4)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        Icon(
            imageVector = EvaIcons.Outline.ChevronRight,
            contentDescription = "See more",
            tint = Color.Gray
        )
    }
}

@Composable
fun TotalAmountSection(value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "You will pay")
            Text(text = "$value")
        }

    }
}
