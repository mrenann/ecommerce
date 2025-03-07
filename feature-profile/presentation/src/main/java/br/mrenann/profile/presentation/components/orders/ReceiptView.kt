package br.mrenann.profile.presentation.components.orders

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import br.mrenann.core.domain.model.Order
import br.mrenann.core.domain.model.OrderProduct
import br.mrenann.core.util.formatBalance
import br.mrenann.navigation.LocalNavigatorParent
import br.mrenann.navigation.SharedScreen
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade


@Composable
fun ReceiptView(
    order: Order,
    totalAmount: String,
    contentColor: Color = Color.Black,
    content: @Composable () -> Unit = @Composable {},

    ) {
    val navigator = LocalNavigatorParent.currentOrThrow
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(order.products.size) {
                    val orderProduct = order.products[it]
                    val detailsScreen =
                        rememberScreen(SharedScreen.ProductDetails(id = orderProduct.id))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.push(detailsScreen)
                            }
                    ) {
                        // Pizza Image and Description
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(orderProduct.images[0])
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(0xFFF1F1F1))
                                    .clip(RoundedCornerShape(6.dp))
                            )
                            Text(
                                text = "${orderProduct.qtd}x ${orderProduct.title}",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }

                        // Pizza Price
                        Text(
                            text = orderProduct.price.formatBalance(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,

                            )
                    }

                }
            }
//            Text(
//                text = "№$orderNumber",
//                style = MaterialTheme.typography.headlineLarge,
//                fontWeight = FontWeight.Bold,
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Pizza Details


            Spacer(modifier = Modifier.height(8.dp))
            DashedLine(color = contentColor)
            Spacer(modifier = Modifier.height(8.dp))

            if ((order.discount != 0.0) && order.priceFinal != order.price) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sub total",
                        style = MaterialTheme.typography.bodyLarge,

                        )
                    Text(
                        text = order.price.formatBalance(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,

                        )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = order.coupon,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Red
                    )
                    Text(
                        text = "- " + order.discount.formatBalance(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red


                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,

                    )
                Text(
                    text = order.priceFinal.formatBalance(),
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
        ReceiptView(
            order = Order(
                priceFinal = 22.0,
                status = "delivered",
                coupon = "",
                products = listOf(
                    OrderProduct(
                        category = br.mrenann.core.domain.model.Category(
                            id = 0,
                            image = "",
                            name = ""
                        ),
                        id = 0,
                        image = "https://gratisography.com/wp-content/uploads/2025/02/gratisography-when-pigs-fly-1170x780.jpg",
                        name = "Bone clássico",
                        description = "asdasdsadasd",
                        price = 22.0,
                    ),
                    OrderProduct(
                        category = br.mrenann.core.domain.model.Category(
                            id = 0,
                            image = "",
                            name = ""
                        ),
                        id = 0,
                        image = "https://gratisography.com/wp-content/uploads/2025/02/gratisography-when-pigs-fly-1170x780.jpg",
                        name = "Bone clássico",
                        description = "asdasdsadasd",
                        price = 22.0,
                    )
                ),
            ),
            totalAmount = "25 $",
            contentColor = Color.DarkGray,
        )
    }
}