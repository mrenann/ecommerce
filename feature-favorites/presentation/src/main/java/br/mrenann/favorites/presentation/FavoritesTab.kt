package br.mrenann.favorites.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Heart

class FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Favoritos"
            val icon = rememberVectorPainter(EvaIcons.Outline.Heart)

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