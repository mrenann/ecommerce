package br.mrenann.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import br.mrenann.favorites.presentation.components.ProductCard
import br.mrenann.favorites.presentation.components.SectionTitle
import br.mrenann.favorites.presentation.components.ShimmerEffect
import br.mrenann.favorites.presentation.screenModel.FavoriteScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Heart
import compose.icons.evaicons.outline.Heart

class FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Favoritos"
            val tabNavigator = LocalTabNavigator.current
            val icon = rememberVectorPainter(EvaIcons.Outline.Heart)
            val iconChecked = rememberVectorPainter(EvaIcons.Fill.Heart)

            return remember(tabNavigator.current.key) {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = if (tabNavigator.current.key == key) iconChecked else icon
                )
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<FavoriteScreenModel>()
        val state by screenModel.state.collectAsState()
        val pullToRefreshState = rememberPullToRefreshState()
        var isRefreshing by remember { mutableStateOf(false) }
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
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
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SectionTitle("Favorites")

                }

                PullToRefreshBox(
                    isRefreshing = isRefreshing && state is FavoriteScreenModel.State.Loading,
                    onRefresh = {
                        isRefreshing = true
                        screenModel.fetch()
                    },
                    state = pullToRefreshState,
                ) {
                    when (state) {
                        is FavoriteScreenModel.State.Loading -> ShimmerEffect()
                        is FavoriteScreenModel.State.Result -> {
                            val products =
                                (state as FavoriteScreenModel.State.Result).state.products

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(products.size) { index -> ProductCard(products[index]) }
                            }
                        }
                    }
                }


            }

        }

    }

}

