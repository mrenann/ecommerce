package br.mrenann.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import br.mrenann.favorites.presentation.components.ProductCard
import br.mrenann.favorites.presentation.components.SectionTitle
import br.mrenann.favorites.presentation.screenModel.FavoriteScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
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
        val screenModel = koinScreenModel<FavoriteScreenModel>()
        val state by screenModel.state.collectAsState()

        when (state) {
            is FavoriteScreenModel.State.Init -> Text("INIT")
            is FavoriteScreenModel.State.Loading -> Text("LOADING")
            is FavoriteScreenModel.State.Result -> {
                val products = (state as FavoriteScreenModel.State.Result).state.products

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                PaddingValues(
                                    top = innerPadding.calculateTopPadding(),
                                    start = innerPadding.calculateStartPadding(
                                        layoutDirection = LayoutDirection.Ltr
                                    ),
                                    bottom = 0.dp,
                                    end = innerPadding.calculateEndPadding(
                                        layoutDirection = LayoutDirection.Ltr
                                    ),
                                )
                            )
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                SectionTitle("Favorites")

                            }
                        }

                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 100.dp, max = 1000.dp)
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    userScrollEnabled = false,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    val productsSize =
                                        if (products.size > 8) 8 else products.size // Limit to 8 items
                                    items(productsSize) { index -> ProductCard(products[index]) }
                                }
                            }

                        }
                    }
                }
            }
        }

    }
}
