package br.mrenann.profile.presentation.components.flipCard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlipCardItem(
    modifier: Modifier = Modifier,
    cardFace: CardFace,
    onClick: (currentCardFace: CardFace) -> Unit,
    front: @Composable () -> Unit = {},
    back: @Composable () -> Unit = {},
) {
    val animateRotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(durationMillis = 800),
        label = "Card Rotation"
    )

    Card(
        onClick = { onClick(cardFace) },
        modifier = modifier
            .graphicsLayer {
                rotationY = animateRotation.value
                cameraDistance = 12f * density
            },
    ) {
        if (animateRotation.value <= 90f) {
            Box {
                front()
            }
        } else {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        rotationY = 180f
                    },
            ) {
                back()
            }
        }
    }
}