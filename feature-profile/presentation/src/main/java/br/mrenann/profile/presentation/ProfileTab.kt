package br.mrenann.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Person
import compose.icons.evaicons.outline.Person

class ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profile"
            val tabNavigator = LocalTabNavigator.current
            val icon = rememberVectorPainter(EvaIcons.Outline.Person)
            val iconChecked = rememberVectorPainter(EvaIcons.Fill.Person)

            return remember(tabNavigator.current.key) {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = if (tabNavigator.current.key == key) iconChecked else icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(ProfileScreen())
    }
}
