package br.mrenann.profile.presentation.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.mrenann.profile.presentation.MenuItem

@Composable
fun UserProfileSection(name: String, email: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        Text(text = "aaa", style = MaterialTheme.typography.bodySmall)
        Text(text = "Orders ", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun MenuItemView(item: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.title, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun MenuSection(menuItems: List<MenuItem>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        menuItems.forEach { item ->
            MenuItemView(item = item)
        }
    }
}