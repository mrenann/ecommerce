package br.mrenann.productdetails.presentation.screenModel

import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.usecase.ProductUseCase
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope

import kotlinx.coroutines.launch


class DetailsScreenModel(
    private val productUseCase: ProductUseCase,
) : StateScreenModel<DetailsScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val product: Product) : State()
    }

    fun getProduct(id: Int) {
        screenModelScope.launch {
            mutableState.value = State.Loading

            val product = productUseCase.invoke(
                params = ProductUseCase.Params(
                    id = id
                )
            )
            mutableState.value = State.Result(product = product)
        }
    }
}