package br.mrenann.main.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import br.mrenann.core.ui.theme.Primary
import cafe.adriel.voyager.navigator.tab.Tab

@Composable
fun RowScope.TabNavigationItem(
    tab: Tab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val selectedColor = Primary
    val unselectedColor = Color.Gray

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = tab.options.icon!!,
                contentDescription = tab.options.title,
                tint = if (selected) selectedColor else unselectedColor
            )
        },
        label = {
            Text(
                tab.options.title,
                color = if (selected) selectedColor else Color.Unspecified
            )
        },
        alwaysShowLabel = true,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = selectedColor,
            unselectedIconColor = unselectedColor,
            selectedTextColor = selectedColor,
            unselectedTextColor = unselectedColor,
            indicatorColor = Color.Transparent
        )
    )
}