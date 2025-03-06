package br.mrenann.profile.presentation.components.orders

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp

@Composable
fun TicketShape() = GenericShape { size, _ ->
    val cornerRadius = 20.dp.value
    val cutoutRadius = 15.dp.value

    moveTo(cornerRadius, 0f)
    lineTo(size.width - cornerRadius, 0f)
    arcTo(
        rect = Rect(
            size.width - cornerRadius * 2, 0f,
            size.width, cornerRadius * 2
        ),
        startAngleDegrees = -90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(size.width, size.height - cornerRadius)
    arcTo(
        rect = Rect(
            size.width - cornerRadius * 2, size.height - cornerRadius * 2,
            size.width, size.height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(cornerRadius, size.height)
    arcTo(
        rect = Rect(
            0f, size.height - cornerRadius * 2,
            cornerRadius * 2, size.height
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(0f, cornerRadius)
    arcTo(
        rect = Rect(
            0f, 0f,
            cornerRadius * 2, cornerRadius * 2
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    close()
}
