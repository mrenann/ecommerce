package br.mrenann.home.presentation.screenModel

import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.usecase.ProductsUseCase
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope

import kotlinx.coroutines.launch


class HomeScreenModel(
    private val productsUseCase: ProductsUseCase,
) : StateScreenModel<HomeScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val products: List<Product>) : State()
    }

    fun getProducts() {
        screenModelScope.launch {
            mutableState.value = State.Loading

            val products = productsUseCase.invoke()
            mutableState.value = State.Result(products = products)
        }
    }

    init {
        getProducts()
    }
}