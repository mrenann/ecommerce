package br.mrenann.profile.presentation.components.orders

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


@Composable
fun PizzaReceiptView(
    orderNumber: String,
    pizzaName: String,
    pizzaQuantity: Int,
    pizzaPrice: String,
    totalAmount: String,
    backgroundColor: Color = Color(0xFFF5F5F5),
    contentColor: Color = Color.Black
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)

    ) {
        Column(
            modifier = Modifier
                //Cut the bottom with a shape
                .clip(
                    TriangleCutBottomShape(with(LocalDensity.current) { 8.dp.toPx() }) // Apply to the content
                )
                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
                .padding(16.dp)
        )
        {
            // Order Number
            Text(
                text = "№$orderNumber",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Pizza Details
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Pizza Image and Description
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "$pizzaQuantity x $pizzaName",
                        style = MaterialTheme.typography.bodyLarge,

                        )
                }

                // Pizza Price
                Text(
                    text = pizzaPrice,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,

                    )
            }

            Spacer(modifier = Modifier.height(16.dp))
            DashedLine(color = contentColor)
            Spacer(modifier = Modifier.height(16.dp))

            // Total Amount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "The order amount",
                    style = MaterialTheme.typography.bodyLarge,

                    )
                Text(
                    text = totalAmount,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,

                    )
            }
        }

    }
}

@Composable
fun DashedLine(color: Color) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(5f, 5f), 0f
                )
            )
        )
    }
}


class TriangleCutBottomShape(private val triangleHeightPx: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                reset()
                moveTo(0f, 0f) // Start at top-left
                lineTo(size.width, 0f) // Top-right
                lineTo(
                    size.width,
                    size.height - triangleHeightPx
                ) // Move down, leaving space for triangles

                // Draw triangles along the bottom
                var currentX = size.width
                while (currentX > 0) {
                    lineTo(currentX - (triangleHeightPx), size.height) // Top-center of triangle
                    currentX -= (triangleHeightPx)
                    lineTo(
                        currentX - (triangleHeightPx),
                        size.height - triangleHeightPx
                    ) // Bottom-right of next triangle
                    currentX -= (triangleHeightPx)

                }

                lineTo(0f, size.height - triangleHeightPx) // Bottom-left
                close() // Close the path
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PizzaReceiptViewPreview() {
    MaterialTheme {
        PizzaReceiptView(
            orderNumber = "12345",
            pizzaName = "Americano",
            pizzaQuantity = 2,
            pizzaPrice = "25 $",
            totalAmount = "25 $",
            backgroundColor = Color.White,
            contentColor = Color.DarkGray
        )
    }
}