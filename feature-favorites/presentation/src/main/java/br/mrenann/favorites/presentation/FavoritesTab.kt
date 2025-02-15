package br.mrenann.favorites.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Star

class FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Favoritos"
            val icon = rememberVectorPainter(EvaIcons.Fill.Star)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Text("FAVORITES TAB")
    }

}