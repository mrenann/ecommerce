package br.mrenann.home.presentation.screenModel

import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.usecase.SearchProductsUseCase
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch


class SearchScreenModel(
    private val searchUseCae: SearchProductsUseCase,
) : StateScreenModel<SearchScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val products: List<Product>) : State()
    }

    fun getProducts(query: String) {
        screenModelScope.launch {
            val products = searchUseCae.invoke(
                params = SearchProductsUseCase.Params(
                    query = query
                )
            )
            mutableState.value = State.Result(
                products = products
            )
        }
    }


}