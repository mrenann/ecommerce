package br.mrenann.profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Order
import br.mrenann.core.util.formatBalance
import br.mrenann.profile.presentation.R
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.CreditCard

@Composable
fun PaidCard(order: Order, color: Color) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            fontSize = 17.sp,
            text = "Paid with",
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = if (order.card.isNullOrBlank().not()
                ) rememberVectorPainter(EvaIcons.Fill.CreditCard) else painterResource(
                    R.drawable.ic_pix
                ),
                contentDescription = null, // Provide a meaningful description
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(color)
            )
            Column {
                Text(
                    text = if (order.card.isNullOrBlank()) "Pix" else order.card ?: "",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = order.priceFinal.formatBalance(),
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

        }
    }
}