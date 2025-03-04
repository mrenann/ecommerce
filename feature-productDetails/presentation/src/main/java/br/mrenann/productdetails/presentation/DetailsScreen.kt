package br.mrenann.productdetails.presentation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.mapper.toProductCart
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import br.mrenann.core.util.formatBalance
import br.mrenann.productdetails.presentation.components.CartBottomSheet
import br.mrenann.productdetails.presentation.components.ShimmerEffect
import br.mrenann.productdetails.presentation.components.SnackBarCustom
import br.mrenann.productdetails.presentation.screenModel.DetailsScreenModel
import br.mrenann.productdetails.presentation.state.DetailsEvent
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.AlertTriangle
import compose.icons.evaicons.fill.Bookmark
import compose.icons.evaicons.outline.Bookmark
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Share
import compose.icons.evaicons.outline.Star
import kotlinx.coroutines.launch

data class DetailsScreen(
    val id: Int
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<DetailsScreenModel>()
        val state by screenModel.state.collectAsState()
        var showBottomSheet by remember { mutableStateOf(false) }
        var snackBarState by remember { mutableStateOf(false) }
        val cartScreenModel = koinScreenModel<CartScreenModel>()
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = id) {
            screenModel.getDetails(DetailsEvent.GetDetails(id))
        }


        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F1F1)),
            snackbarHost = {
                SnackBarCustom(
                    snackbarHostState = snackbarHostState,
                    snackBarState = snackBarState
                )
            }
        ) { innerPadding ->
            Column(Modifier.fillMaxSize()) {

                when (state) {
                    is DetailsScreenModel.State.Init -> {
                        ShimmerEffect(innerPadding) { navigator.pop() }
                    }

                    is DetailsScreenModel.State.Loading -> {
                        Text("CARREGANDO...")
                    }

                    is DetailsScreenModel.State.Result -> {
                        val product = (state as DetailsScreenModel.State.Result).state.product
                        val checkedState by rememberUpdatedState(
                            newValue = (state as? DetailsScreenModel.State.Result)?.state?.checked
                                ?: false
                        )

                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        product?.images?.get(0)
                                            ?: "aaa"
                                    )
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.35F)
                                    .background(Color(0xFFF1F1F1))
                                    .clickable {
                                        navigator.push(
                                            ImageDetailsScreen(
                                                images = product?.images ?: emptyList()
                                            )
                                        )
                                    }
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = innerPadding.calculateTopPadding()),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = {
                                    navigator.pop()
                                }) {
                                    innerPadding
                                    Icon(
                                        tint = Color.Black,
                                        imageVector = EvaIcons.Outline.ChevronLeft,
                                        contentDescription = "Localized description",
                                    )
                                }
                                Row {
                                    IconButton(onClick = {
                                        if (product != null) {
                                            screenModel.favorite(item = product)

                                            coroutineScope.launch {
                                                snackBarState = !checkedState
                                                snackbarHostState.currentSnackbarData?.dismiss()
                                                snackbarHostState.showSnackbar(
                                                    message = if (checkedState) "Product removed from favorites" else "Product added to favorites",
                                                    duration = SnackbarDuration.Short,
                                                )
                                            }
                                        }

                                    }) {
                                        innerPadding
                                        Icon(
                                            tint = Color.Black,
                                            imageVector = if (checkedState) EvaIcons.Fill.Bookmark else EvaIcons.Outline.Bookmark,
                                            contentDescription = "Bookmark"
                                        )
                                    }
                                    IconButton(onClick = {
                                        product?.let {
                                            val title =
                                                "Check this product: \n${it.title}\n${it.price.formatBalance()}"

                                            val shareIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_TEXT, title)
                                                type = "text/plain"
                                            }

                                            context.startActivity(
                                                Intent.createChooser(
                                                    shareIntent,
                                                    "Share via"
                                                )
                                            )
                                        }
                                    }) {
                                        Icon(
                                            tint = Color.Black,
                                            imageVector = EvaIcons.Outline.Share,
                                            contentDescription = "Share"
                                        )
                                    }

                                }
                            }

                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                                )
                                .padding(horizontal = 12.dp)
                                .padding(top = 16.dp)
                        ) {
                            Text(
                                text = product?.title ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = product?.category?.name ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 16.sp,
                                    color = Color.Gray

                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(14.dp),
                                        imageVector = EvaIcons.Outline.Star,
                                        contentDescription = "Search Icon",
                                        tint = Color(0xFFFFAA39)
                                    )
                                    Text(
                                        text = "4.9",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 16.sp,
                                        color = Color.Gray
                                    )

                                }
                            }
                            Text(
                                text = product?.description ?: "",
                                fontSize = 16.sp,
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(horizontal = 12.dp, vertical = 16.dp),
                            thickness = 1.dp,
                            color = Color(0xFFF1F1F1)
                        )
                        Row(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(
                                    bottom = innerPadding.calculateBottomPadding(),
                                )
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Text(
                                text = (product?.price ?: 0).formatBalance(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                modifier = Modifier.weight(1F),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    if (product != null) cartScreenModel.addProduct(product = product.toProductCart())
                                    showBottomSheet = true
                                }
                            ) {
                                Text("Add to Cart")
                            }
                        }

                        if (showBottomSheet) {
                            ModalBottomSheet(
                                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                                onDismissRequest = { showBottomSheet = false },
                                sheetState = rememberModalBottomSheetState()
                            ) {
                                CartBottomSheet(product) { showBottomSheet = false }
                            }
                        }

                    }

                    is DetailsScreenModel.State.Error -> {


                        Column {
                            Box {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.35F)
                                        .background(Color(0xFFF1F1F1)),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center

                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("") // Se for null, passa uma string vazia
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Product Image",
                                        contentScale = ContentScale.FillHeight,
                                        error = rememberVectorPainter(image = EvaIcons.Fill.AlertTriangle),
                                        modifier = Modifier.fillMaxHeight(.3F)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = innerPadding.calculateTopPadding()),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    IconButton(onClick = {
                                        navigator.pop()
                                    }) {
                                        Icon(
                                            tint = Color.Black,
                                            imageVector = EvaIcons.Outline.ChevronLeft,
                                            contentDescription = "Localized description",
                                        )
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1F)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                                    )
                                    .padding(horizontal = 12.dp)
                                    .padding(top = 16.dp)
                            ) {
                                Text(
                                    text = "PRODUCT NOT FOUND",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                }
            }

        }

    }


}