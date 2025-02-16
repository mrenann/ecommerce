package br.mrenann.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.home.presentation.components.CategoryCard
import br.mrenann.home.presentation.components.ProductCard
import br.mrenann.home.presentation.components.SearchBar
import br.mrenann.home.presentation.components.SectionTitle
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Home
import compose.icons.evaicons.outline.ShoppingCart

class HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Inicio"
            val icon = rememberVectorPainter(EvaIcons.Fill.Home)

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
        var searchQuery by remember { mutableStateOf("") }
        val categories = listOf<String>(
            "Mobile",
            "Headphone",
            "Tablets",
            "Laptop",
            "Speakers",
            "Clothes",
            "Foods"
        )
        val products = listOf<String>(
            "Mobile",
            "Headphone",
            "Tablets",
            "Laptop",
            "Speakers",
            "Clothes",
            "Foods",
            "Clothes",
            "Foods"
        )

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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SectionTitle("Discover")
                        IconButton(onClick = {}) {
                            Icon(
                                tint = Color.Black,
                                imageVector = EvaIcons.Outline.ShoppingCart,
                                contentDescription = "Localized description",
                            )
                        }
                    }
                }

                item {
                    SearchBar(query = searchQuery, onQueryChange = { searchQuery = it })
                }

                item {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .background(Color(0xFF3B73FF), RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        AsyncImage(
                            modifier = Modifier.background(Color.Black, RoundedCornerShape(8.dp)),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://github.com/mrenann/mrenann/blob/master/img/1.png?raw=true")
                                .crossfade(true)
                                .build(),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                        )
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 14.dp)
                                .fillMaxWidth(.5F)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp,
                                text = "Buy your electronics",
                                color = Color.White
                            )
                        }
                    }
                }

                item { SectionTitle("Categories") }

                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp), // Set a fixed height here
                        userScrollEnabled = false
                    ) {
                        val modifiedCategories = categories.take(5).toMutableList().apply {
                            if (categories.size > 5) add("More")
                        }
                        items(modifiedCategories.size) { index ->
                            val category = modifiedCategories[index]
                            CategoryCard(category)
                        }
                    }
                }

                item { SectionTitle("For You") }

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
                            items(productsSize) { ProductCard() }
                        }
                    }
                }

                item {
                    Text("ACABOU SE O QUE ERA DOCE")
                }

            }
        }


    }

}
