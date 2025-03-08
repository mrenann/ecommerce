package br.mrenann.profile.presentation.components.orders

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerOrders() {
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        items(8) { order ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .background(skeletonColor)
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add spacing between cards
        }
    }
}