package br.mrenann.favorites.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Category
import br.mrenann.core.domain.model.Product
import br.mrenann.core.util.formatBalance
import br.mrenann.navigation.LocalNavigatorParent
import br.mrenann.productdetails.presentation.DetailsScreen
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Star

@Composable
fun ProductCard(product: Product) {
    val navigator = LocalNavigatorParent.currentOrThrow
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigator.push(DetailsScreen(product.id))
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF1F1F1),
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        product.images[0]
                            ?: "aaa"
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .padding(top = 3.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(0.75F),
                    text = product.title,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.weight(0.25F),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = EvaIcons.Fill.Star,
                        contentDescription = "Search Icon",
                        tint = Color(0xFFFFAA39)
                    )
                    Text(
                        text = "4.9",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
            Text(
                product.price.formatBalance(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    ProductCard(
        Product(
            id = 1,
            title = "Handmade Fresh Table",
            price = 687,
            description = "Andy shoes are designed to keeping in...",
            category = Category(
                id = 12,
                image = "https://placeimg.com/640/480/any?r=0.591926261873231",
                name = "Others"
            ),
            images = listOf(
                "https://placeimg.com/640/480/any?r=0.9178516507833767",
                "https://placeimg.com/640/480/any?r=0.9300320592588625",
                "https://placeimg.com/640/480/any?r=0.8807778235430017"
            )
        )
    )
}