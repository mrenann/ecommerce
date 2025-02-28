package br.mrenann.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Archive
import compose.icons.fontawesomeicons.solid.Book
import compose.icons.fontawesomeicons.solid.Couch
import compose.icons.fontawesomeicons.solid.EllipsisH
import compose.icons.fontawesomeicons.solid.Headphones
import compose.icons.fontawesomeicons.solid.ShoePrints
import compose.icons.fontawesomeicons.solid.ThLarge

@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {
    val icon = when (category) {
        "More" -> FontAwesomeIcons.Solid.ThLarge
        "Furniture" -> FontAwesomeIcons.Solid.Couch
        "Shoes" -> FontAwesomeIcons.Solid.ShoePrints
        "Electronics" -> FontAwesomeIcons.Solid.Headphones
        "Books" -> FontAwesomeIcons.Solid.Book
        "Miscellaneous" -> FontAwesomeIcons.Solid.Archive
        else -> FontAwesomeIcons.Solid.EllipsisH
    }

    Column(
        modifier = Modifier
            .background(color = Color(0xFFF1F1F1), shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.size(56.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = icon,
                contentDescription = "$category Icon",
            )
        }
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = category
        )
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard("More") {

    }
}