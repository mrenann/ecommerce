package br.mrenann.home.presentation.screenModel

import br.mrenann.core.domain.model.Product
import br.mrenann.home.domain.usecase.ProductsByCategoryUseCase
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch


class CategoryScreenModel(
    private val productsUseCase: ProductsByCategoryUseCase,
) : StateScreenModel<CategoryScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val products: List<Product>) : State()
    }

    fun getProductsByCategory(id: String) {
        screenModelScope.launch {
            val products = productsUseCase.invoke(
                params = ProductsByCategoryUseCase.Params(
                    id = id
                )
            )
            mutableState.value = State.Result(
                products = products
            )
        }
    }


}