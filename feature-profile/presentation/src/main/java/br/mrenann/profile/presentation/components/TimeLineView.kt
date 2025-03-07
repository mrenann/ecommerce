package br.mrenann.profile.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.mrenann.profile.presentation.components.orders.TimelineStep


@Composable
fun TimelineView(timeline: List<TimelineStep>) {
    LazyColumn(
    ) {
        itemsIndexed(timeline) { index, step ->
            val isLastCompleted = timeline.indexOfLast { it.isCompleted } == index
            TimelineStepView(
                step = step,
                isLast = index == timeline.size - 1,
                isLastCompleted = isLastCompleted
            )
        }
    }
}

@Composable
fun TimelineStepView(step: TimelineStep, isLast: Boolean, isLastCompleted: Boolean) {
    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Timeline Indicator (Circle and Line)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val color = if (step.isCompleted) Color.White else Color.DarkGray

            Canvas(
                modifier = Modifier
                    .size(24.dp)
            ) {
                drawCircle(color = color, radius = size.minDimension / 3)
            }

            if (!isLast) { // Don't draw the line for the last step
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(if (isLastCompleted) 32.dp else 8.dp) // Adjust height as needed
                        .background(color)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Step Details
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            val color = if (step.isCompleted) Color.White else Color.DarkGray

            Text(
                text = step.status.replace("_", " "),
                fontWeight = FontWeight.Bold,
                color = color
            )
            // Show description only if the step is completed and it's the last completed step
            if (isLastCompleted && step.isCompleted) {
                step.description?.let {
                    Text(text = it, color = Color.White)
                }
            }
        }
    }
}