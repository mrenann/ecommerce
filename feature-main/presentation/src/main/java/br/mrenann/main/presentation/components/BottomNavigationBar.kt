package br.mrenann.main.presentation.components

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

@Composable
internal fun BottomNavigationBar(tabs: List<Tab>) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBar {
        tabs.forEach { tab ->
            TabNavigationItem(
                tab = tab,
                selected = tabNavigator.current.key == tab.key,
                onClick = {
                    tabNavigator.current = tab
                }
            )
        }
    }
}