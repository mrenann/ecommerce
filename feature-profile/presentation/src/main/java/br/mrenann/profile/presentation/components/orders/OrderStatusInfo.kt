package br.mrenann.profile.presentation.components.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import br.mrenann.core.domain.model.OrderStatus

@Composable
fun OrderStatusInfo(
    status: OrderStatus,
    subtitle: String = ""
) {
    Column(
        modifier = Modifier
            .background(color = status.color, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
           text = status.displayName,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        if(subtitle.isNotBlank()) {
            Text(
                text = subtitle,
                color = Color.White,
                fontSize = 16.sp
            )
        }

    }
}

@Composable
@Preview
fun OrderStatusInfoPreview() {
    OrderStatusInfo(status = OrderStatus.PAID, subtitle = "Tuesday, October 1, 16:45")
}