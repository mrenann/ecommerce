package br.mrenann.home.presentation.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Category
import br.mrenann.home.presentation.components.ProductsContent
import br.mrenann.home.presentation.components.ShimmerCategoryProducts
import br.mrenann.home.presentation.screenModel.CategoryScreenModel
import br.mrenann.navigation.LocalNavigatorParent
import br.mrenann.productdetails.presentation.DetailsScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft

data class CategoryScreen(
    val category: Category
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigatorParent.currentOrThrow
        val navigatorChildren = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<CategoryScreenModel>()
        screenModel.getProductsByCategory(category.id.toString())
        val state by screenModel.state.collectAsState()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
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
                    .padding(horizontal = 12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigatorChildren.pop() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = "${category.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )

                }

                when (state) {
                    is CategoryScreenModel.State.Init -> {
                        ShimmerCategoryProducts()

                    }

                    is CategoryScreenModel.State.Loading -> {}
                    is CategoryScreenModel.State.Result -> {
                        val products = (state as CategoryScreenModel.State.Result).products
                        ProductsContent(
                            products = products,
                            navigate = { id -> navigator.push(DetailsScreen(id)) }
                        )
                    }
                }

            }
        }
    }

}
