package br.mrenann.productdetails.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Category
import br.mrenann.core.domain.model.Product
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Close

@Composable
fun CartBottomSheet(product: Product?, onClose: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {onClose()}) {
                Icon(
                    tint = Color.Black,
                    imageVector = EvaIcons.Outline.Close,
                    contentDescription = "Localized description",
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        product?.images?.get(0) ?: ""
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFFF1F1F1))
                    .clip(RoundedCornerShape(8.dp))
            )
            Column {
                Text(
                    text = "Added to Cart",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 16.sp

                )
                Text(
                    text = product?.title ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )
                Text(
                    text = "1 unit",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 11.sp,
                    lineHeight = 11.sp
                )
            }


        }


    }
}

@Preview(
    showBackground = true,
)
@Composable
fun CartBottomSheetPreview() {
    CartBottomSheet(
        Product(
            id = 1,
            title = "Classic Navy Blue Baseball Cap",
            price = 22,
            description = "",
            category = Category(
                id = 1,
                name = "Capas",
                image = ""
            ),
            images = listOf("")
        )
    ) { }
}