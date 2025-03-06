package br.mrenann.profile.presentation.components.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.mrenann.core.domain.model.Order
import br.mrenann.core.domain.model.formatOrderStatus
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            val formattedDate = order.createdAt?.let {
                val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                format.format(it)
            } ?: "Data não disponível"
            val (title, subtitle, color) = order.status.formatOrderStatus()

            Text(
                modifier = Modifier.padding(8.dp),
                text = "$formattedDate",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(Modifier, 1.dp, Color.LightGray)
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(order.products[0].images[0])
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = color
                    )

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "${order.products.size} product",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        }
    }
}