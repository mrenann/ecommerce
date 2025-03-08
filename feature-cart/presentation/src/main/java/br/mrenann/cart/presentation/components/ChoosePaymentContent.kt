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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.Card
import br.mrenann.cart.presentation.R
import br.mrenann.core.util.formatBalance
import cafe.adriel.lyricist.LocalStrings
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronRight

@Composable
fun ChoosePaymentContent(
    goToNext: (String, Card?) -> Unit,
    cards: List<Card>,
    value: Double,
    discountValue: Double = 0.0,
    discountCode: String?,
) {
    val strings = LocalStrings.current.cartScreen
    Column {
        LazyColumn(
            modifier = Modifier.weight(1F),
        ) {
            item {
                PaymentOption(
                    icon = painterResource(R.drawable.ic_pix), // Replace
                    title = strings.pix,
                    subtitle = strings.immediateApproval,
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

        TotalAmountSection(
            value = value,
            discountValue = discountValue,
        )
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
    val strings = LocalStrings.current.cartScreen

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
                text = strings.recommended.toUpperCase(Locale.current),
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
    val strings = LocalStrings.current.cartScreen

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
                Text(text = strings.card, fontWeight = FontWeight.Bold)
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
fun TotalAmountSection(value: Double, discountValue: Double = 0.0) {
    val strings = LocalStrings.current.cartScreen

    val totalValue = discountValue + value
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = strings.youWillPay)
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = value.formatBalance())
                if (totalValue != value) {
                    Text(
                        text = totalValue.formatBalance(),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }


        }

    }
}

@Preview
@Composable
fun PreviewChoosePaymentContent() {
    TotalAmountSection(
        value = 100.0,
    )
}


