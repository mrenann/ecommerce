package br.mrenann.cart.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.ProductCart
import br.mrenann.core.util.formatBalance
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Minus
import compose.icons.evaicons.outline.Plus

@Composable
fun CartItem(product: ProductCart, onDecrease: () -> Unit, onIncrement: () -> Unit) {
    val quantity = remember { mutableIntStateOf(product.qtd) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .size(76.dp)
                    .padding(end = 12.dp)
                    .background(Color(0xFFF1F1F1))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            product.images[0]
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF1F1F1))
                )
            }


            Column {
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = product.category.name,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        val priceNotEquals = product.priceFinal != product.price * product.qtd
                        if (priceNotEquals) {
                            Text(
                                text = product.priceFinal.formatBalance(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = product.price.formatBalance(),
                            style = MaterialTheme.typography.bodyLarge,
                            textDecoration = if (priceNotEquals) TextDecoration.LineThrough else null,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (priceNotEquals) Color.Gray else Color.Black
                        )
                    }

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier.size(36.dp),
                            border = BorderStroke(
                                1.dp,
                                if (quantity.intValue == 1) Color.LightGray else Color(0xFF3F51B5)
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Black,
                                contentColor = Color.Black
                            ),
                            enabled = quantity.intValue != 1,
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onDecrease()
                                quantity.intValue -= 1
                            }
                        ) {
                            Icon(
                                tint = Color.Black,
                                imageVector = EvaIcons.Outline.Minus,
                                contentDescription = "Localized description",
                            )
                        }
                        Text(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            text = "${quantity.intValue}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            modifier = Modifier.size(36.dp),
                            border = BorderStroke(1.dp, Color(0xFF3F51B5)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Black,
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                onIncrement()
                                quantity.intValue += 1
                            }
                        ) {
                            Icon(
                                tint = Color.Black,
                                imageVector = EvaIcons.Outline.Plus,
                                contentDescription = "Localized description",
                            )
                        }
                    }
                }

            }
        }

    }
}
