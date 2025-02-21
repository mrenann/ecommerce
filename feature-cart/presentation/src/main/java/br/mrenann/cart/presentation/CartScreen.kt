package br.mrenann.cart.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.cart.presentation.components.CartItem
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft
import compose.icons.evaicons.outline.Trash

class CartScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<CartScreenModel>()
        val state by screenModel.state.collectAsState()
        screenModel.getProducts()

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {

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
                    Text(
                        text = "Cart",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = {
                        screenModel.clearCart()
                    }) {
                        Icon(
                            tint = Color.Black,
                            imageVector = EvaIcons.Outline.Trash,
                            contentDescription = "Localized description",
                        )
                    }
                }

                when (state) {
                    is CartScreenModel.State.Init -> {
                        Log.i("TAG", "Init")

                    }

                    is CartScreenModel.State.Loading -> {
                        Log.i("TAG", "Loading")

                    }

                    is CartScreenModel.State.Result -> {
                        val result = state as CartScreenModel.State.Result
                        Log.i("TAG", "$result")
                        LazyColumn(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(result.state.products.size) { index ->
                                CartItem(result.state.products[index])
                            }
                        }
                    }
                }


            }


        }
    }
}