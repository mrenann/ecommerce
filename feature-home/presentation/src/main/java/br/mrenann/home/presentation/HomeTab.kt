package br.mrenann.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import br.mrenann.home.presentation.screen.HomeScreen
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Home
import compose.icons.evaicons.outline.Home

class HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = LocalStrings.current.homeTab.title
            val tabNavigator = LocalTabNavigator.current
            val icon = rememberVectorPainter(EvaIcons.Outline.Home)
            val iconChecked = rememberVectorPainter(EvaIcons.Fill.Home)

            return remember(tabNavigator.current.key) {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = if (tabNavigator.current.key == key) iconChecked else icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(HomeScreen())
    }
}

