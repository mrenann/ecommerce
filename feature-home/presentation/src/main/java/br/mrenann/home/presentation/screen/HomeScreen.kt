package br.mrenann.home.presentation.screen

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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.CartScreen
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import br.mrenann.core.domain.model.Category
import br.mrenann.home.presentation.components.CategoryCard
import br.mrenann.home.presentation.components.ProductCard
import br.mrenann.home.presentation.components.SearchBar
import br.mrenann.home.presentation.components.SectionTitle
import br.mrenann.home.presentation.components.ShimmerHome
import br.mrenann.home.presentation.screenModel.HomeScreenModel
import br.mrenann.navigation.LocalNavigatorParent
import br.mrenann.productdetails.presentation.DetailsScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ShoppingCart

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        var searchQuery by remember { mutableStateOf("") }
        val navigator = LocalNavigatorParent.currentOrThrow
        val navigatorChildren = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<HomeScreenModel>()
        val state by screenModel.state.collectAsState()

        val cartScreenModel = koinScreenModel<CartScreenModel>()
        val cartState by cartScreenModel.state.collectAsState()
        cartScreenModel.countItemsFromCart()
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)

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
                when (state) {
                    is HomeScreenModel.State.Init -> ShimmerHome()

                    is HomeScreenModel.State.Loading -> {

                    }

                    is HomeScreenModel.State.Result -> {
                        val products = (state as HomeScreenModel.State.Result).products
                        val categories = (state as HomeScreenModel.State.Result).categories
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    SectionTitle("Discover")
                                    IconButton(onClick = {
                                        navigator.push(CartScreen())
                                    }) {

                                        BadgedBox(
                                            badge = {
                                                if (cartState is CartScreenModel.State.Result) {
                                                    val cartItemCount =
                                                        (cartState as CartScreenModel.State.Result).state.itemsCount
                                                    if (cartItemCount > 0) { // Exibir apenas se houver itens no carrinho
                                                        Badge(
                                                            containerColor = Color(0xFF1373FF)
                                                        ) {
                                                            Text(text = cartItemCount.toString())
                                                        }
                                                    }
                                                }

                                            }
                                        ) {
                                            Icon(
                                                imageVector = EvaIcons.Outline.ShoppingCart,
                                                contentDescription = "Shopping Cart",
                                            )
                                        }
                                    }
                                }
                            }

                            item {
                                SearchBar(
                                    query = searchQuery,
                                    onQueryChange = { searchQuery = it },
                                    onSearch = {
                                        navigatorChildren.push(SearchScreen(it))
                                    }
                                )
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
                                        modifier = Modifier.background(
                                            Color.Black,
                                            RoundedCornerShape(8.dp)
                                        ),
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
                                        .heightIn(
                                            min = 100.dp,
                                            max = 240.dp
                                        ), // Set a fixed height here
                                    userScrollEnabled = false
                                ) {
                                    val modifiedCategories =
                                        categories.take(5).toMutableList().apply {
                                            if (categories.size > 5) add(
                                                Category(
                                                    id = 0,
                                                    image = "",
                                                    name = "More",
                                                )
                                            )
                                        }
                                    items(modifiedCategories.size) { index ->
                                        val category = modifiedCategories[index]
                                        CategoryCard(category = category.name, onClick = {
                                            if (category.id == 0) navigatorChildren.push(
                                                CategoriesScreen()
                                            ) else navigatorChildren.push(
                                                CategoryScreen(category = category)
                                            )
                                        })
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
                                        items(productsSize) { index ->
                                            ProductCard(products[index]) {
                                                navigator.push(
                                                    DetailsScreen(products[index].id)
                                                )
                                            }
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }

    }
}