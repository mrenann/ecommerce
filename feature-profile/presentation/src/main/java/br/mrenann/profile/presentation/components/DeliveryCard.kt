package br.mrenann.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import br.mrenann.core.domain.model.Order
import br.mrenann.core.domain.model.OrderStatus
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Person
import compose.icons.evaicons.fill.Pin

@Composable
fun DeliveryCard(order: Order, color: Color) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            fontSize = 17.sp,
            text = "Delivery To",
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = EvaIcons.Fill.Person,
                contentDescription = "Search Icon",
                tint = color
            )
            Text(
                text = order.sendTo,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                style = MaterialTheme.typography.bodyLarge,
            )

        }
        Spacer(Modifier.size(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = EvaIcons.Fill.Pin,
                contentDescription = "Search Icon",
                tint = color
            )
            Column {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = order.typeAddress,
                    fontSize = 15.sp,
                    lineHeight = 15.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = order.location,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

        }
    }
}

@Preview
@Composable
fun DeliveryCardPreview() {
    DeliveryCard(
        Order(
            sendTo = "Marcos Renann",
            typeAddress = "Residence",
            location = "Rua dos bobos, 0"
        ), OrderStatus.PAID.color
    )

}