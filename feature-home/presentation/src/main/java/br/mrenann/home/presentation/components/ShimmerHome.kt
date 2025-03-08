package br.mrenann.home.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings


@Composable
fun ShimmerHome() {
    val infiniteTransition =
        rememberInfiniteTransition(label = "infiniteTransition")
    val localConfig = LocalConfiguration.current
    val target = (localConfig.screenWidthDp * 3).toFloat()
    val scale by
    infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = target,
        animationSpec =
            infiniteRepeatable(
                animation = tween(375, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "shimmer"
    )

    val skeletonColor = Brush.linearGradient(
        colors = listOf(
            Color.Gray.copy(alpha = 0.5F),
            Color.Gray.copy(alpha = 0.1F),
            Color.Gray.copy(alpha = 0.5F),
        ),
        end = Offset(x = scale, y = scale)
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SectionTitle(LocalStrings.current.homeTab.discover)

            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(skeletonColor)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .background(skeletonColor, RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        item {
            Box(
                modifier = Modifier
                    .background(skeletonColor)
                    .fillMaxWidth(.5F)
                    .height(32.dp)
            )
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 100.dp,
                        max = 240.dp
                    ), // Set a fixed height here
                userScrollEnabled = false
            ) {

                items(6) { index ->
                    Column(
                        modifier = Modifier
                            .background(
                                brush = skeletonColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.size(56.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                        }
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = ""
                        )
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .background(skeletonColor)
                    .fillMaxWidth(.5F)
                    .height(32.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 1000.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(4) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth()
                                    .background(
                                        skeletonColor,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .padding(top = 3.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .fillMaxWidth()
                                        .background(
                                            skeletonColor
                                        )

                                )
                            }


                        }

                    }
                }
            }

        }

    }
}