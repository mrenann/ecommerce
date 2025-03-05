package br.mrenann.home.presentation.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Category
import br.mrenann.home.presentation.R
import br.mrenann.home.presentation.components.ShimmerCategories
import br.mrenann.home.presentation.screenModel.HomeScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft

class CategoriesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {


                ScreenContent(
                    navigatePop = { navigator.pop() },
                )

            }
        }
    }

    @Composable
    fun ScreenContent(
        navigatePop: () -> Boolean,
    ) {
        val navigator = LocalNavigator.currentOrThrow

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigatePop() }) {
                    Icon(
                        imageVector = EvaIcons.Outline.ChevronLeft,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )

            }
            val screenModel = koinScreenModel<HomeScreenModel>()
            val state by screenModel.state.collectAsState()
            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
            ) {
                when (state) {
                    is HomeScreenModel.State.Init -> {
                        ShimmerCategories()
                    }

                    is HomeScreenModel.State.Loading -> {}
                    is HomeScreenModel.State.Result -> {
                        val categories = (state as HomeScreenModel.State.Result).categories
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(categories.size) { index ->
                                val category = categories[index]
                                CategoryCard(
                                    category = category
                                ) {
                                    navigator.push(CategoryScreen(category))
                                }
                            }
                        }
                    }
                }
            }

        }

    }

    @Composable
    fun CategoryCard(category: Category, onClick: () -> Unit) {

        Column(
            modifier = Modifier
                .height(100.dp)
                .background(
                    color = Color(0xFFF1F1F1),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    onClick()
                }
        ) {
            Box(contentAlignment = Alignment.BottomStart) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            category.image
                        )
                        .crossfade(true)
                        .placeholder(R.drawable.ic_shoppingcart)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Text(
                    text = category.name,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }


        }
    }


    @Composable
    @Preview
    fun CategoriesContentPreview() {
        CategoryCard(
            category = Category(
                id = 1,
                image = "https://imgur.com/ZANVnHE",
                name = "Electronics"
            ),
            onClick = { }
        )
    }
}
