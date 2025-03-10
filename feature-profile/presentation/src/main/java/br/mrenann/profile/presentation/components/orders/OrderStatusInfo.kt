package br.mrenann.profile.presentation.components.orders

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.OrderStatus
import br.mrenann.profile.presentation.components.TimelineView
import cafe.adriel.lyricist.LocalStrings
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Link

@Composable
fun OrderStatusInfo(
    status: OrderStatus,
    subtitle: String = "",
    isPix: Boolean = false
) {
    val strings = LocalStrings.current.profileTab.orders
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(color = status.color, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(22.dp),
    ) {
        Text(
            text = strings.ordersStatusTitle(status),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        if (subtitle.isNotBlank()) {
            Text(
                text = subtitle,
                color = Color.White,
                fontSize = 16.sp
            )
        }

        if (isPix) {
            val pixCode =
                "00020126540014br.gov.bcb.pix0132pix.br@bankingecommerce.br123.gov"
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                        .weight(1F),
                    text = pixCode,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Pix Code", pixCode)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Code copied!", Toast.LENGTH_SHORT)
                        .show()
                }) {
                    Icon(
                        imageVector = EvaIcons.Outline.Link,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }

        if (status == OrderStatus.ON_THE_WAY) {
            TimelineView(
                listOf(
                    TimelineStep(
                        strings.shippedTitle,
                        true,
                        strings.shippedSubtitle
                    ),
                    TimelineStep(
                        strings.onTheRoadTitle,
                        true,
                        strings.onTheRoadSubtitle
                    ),
                    TimelineStep(
                        strings.dispatchedTitle,
                        false,
                        strings.dispatchedSubtitle
                    ),
                    TimelineStep(
                        strings.enRouteTitle,
                        false,
                        strings.enRouteSubtitle
                    ),
                )
            )
        }
    }
}


data class TimelineStep(
    val status: String,
    val isCompleted: Boolean,
    val description: String? = null // Optional description for the step
)

enum class OrderStatusEnum {
    SHIPPED,
    ON_THE_ROAD,
    DISPATCHED,
    EN_ROUTE,
}


@Composable
@Preview
fun OrderStatusInfoPreview() {
    OrderStatusInfo(status = OrderStatus.ON_THE_WAY, subtitle = "")
}