package br.mrenann.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.CardBrand
import br.mrenann.profile.presentation.screens.Card
import cafe.adriel.lyricist.LocalStrings
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Americanexpress
import compose.icons.simpleicons.Bandcamp
import compose.icons.simpleicons.Discover
import compose.icons.simpleicons.Ello
import compose.icons.simpleicons.Jcb
import compose.icons.simpleicons.Mastercard
import compose.icons.simpleicons.Visa

@Composable
fun CardItem(card: Card, index: Int, modifier: Modifier = Modifier) {
    val backgroundColor = if (index % 2 == 0) Color(0xFFFF7F33) else Color(0xFF735DFF)
    val icon = if (card.type.isBlank().not()) {
        when (CardBrand.valueOf(card.type)) {
            CardBrand.VISA -> SimpleIcons.Visa
            CardBrand.MASTERCARD -> SimpleIcons.Mastercard
            CardBrand.AMEX -> SimpleIcons.Americanexpress
            CardBrand.ELO -> SimpleIcons.Ello
            CardBrand.HIPERCARD -> SimpleIcons.Mastercard
            CardBrand.DINERS -> SimpleIcons.Mastercard
            CardBrand.DISCOVER -> SimpleIcons.Discover
            CardBrand.JCB -> SimpleIcons.Jcb
            CardBrand.UNKNOWN -> SimpleIcons.Bandcamp
            else -> SimpleIcons.Bandcamp
        }
    } else {
        SimpleIcons.Bandcamp
    }

    val strings = LocalStrings.current.profileTab.myCards

    Column(
        modifier = modifier
            .width(200.dp)
            .height(185.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Icon(
            tint = Color.White,
            imageVector = icon,
            contentDescription = "Search Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = LocalStrings.current.cartScreen.cardNumber(card.cardNumber.takeLast(4)),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "${strings.exp} ${card.expiryDate}", fontSize = 14.sp, color = Color.White)
    }
}