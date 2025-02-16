package br.mrenann.productdetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Heart
import compose.icons.evaicons.outline.Share
import compose.icons.evaicons.outline.Star

class DetailsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F1F1))
        ) { innerPadding ->
            Column(Modifier.fillMaxSize()) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                "https://cms-assets.xboxservices.com/assets/bc/40/bc40fdf3-85a6-4c36-af92-dca2d36fc7e5.png?n=642227_Hero-Gallery-0_A1_857x676.png"
                                    ?: "aaa"
                            )
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.35F)
                            .background(Color(0xFFF1F1F1))
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = innerPadding.calculateTopPadding()),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = {
                            navigator.pop()
                        }) {
                            innerPadding
                            Icon(
                                tint = Color.Black,
                                imageVector = EvaIcons.Outline.ChevronLeft,
                                contentDescription = "Localized description",
                            )
                        }
                        Row {
                            IconButton(onClick = {}) {
                                innerPadding
                                Icon(
                                    tint = Color.Black,
                                    imageVector = EvaIcons.Outline.Heart,
                                    contentDescription = "Localized description",
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(
                                    tint = Color.Black,
                                    imageVector = EvaIcons.Outline.Share,
                                    contentDescription = "Localized description",
                                )
                            }
                        }
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                        .padding(horizontal = 12.dp)
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Xbox Series X",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Electronic",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp,
                            color = Color.Gray

                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = EvaIcons.Outline.Star,
                                contentDescription = "Search Icon",
                                tint = Color(0xFFFFAA39)
                            )
                            Text(
                                text = "4.9",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )

                        }
                    }
                    Text(
                        text = "Introducing Xbox Series X, the fastest, most powerful Xbox ever." +
                                " Play thousands of titles from four generations of consoles—all games" +
                                " look and play best on Xbox Series X. Limit 3 per customer.",
                        fontSize = 16.sp,

                        )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Text(
                            text = "R$4500,00",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {}
                        ) {
                            Text("Add to Cart")
                        }
                    }

                }

            }
        }
    }
}