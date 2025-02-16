package br.mrenann.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Grid
import compose.icons.evaicons.fill.PlayCircle

@Composable
fun CategoryCard(category: String) {

    Column(
        modifier = Modifier
            .background(color = Color(0xFFF1F1F1), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .size(56.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = if (category == "More") EvaIcons.Fill.Grid else EvaIcons.Fill.PlayCircle,
                contentDescription = "Search Icon"
            )
        }
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = "$category"
        )
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard("More")
}