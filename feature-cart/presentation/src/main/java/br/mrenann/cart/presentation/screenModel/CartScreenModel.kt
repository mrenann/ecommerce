package br.mrenann.cart.presentation.screenModel

import br.mrenann.cart.domain.usecase.AddCartUseCase
import br.mrenann.cart.domain.usecase.AddCartUseCase.Params
import br.mrenann.cart.domain.usecase.ClearCartUseCase
import br.mrenann.cart.domain.usecase.GetProductsFromCartUseCase
import br.mrenann.cart.presentation.screenModel.CartScreenModel.State.Result
import br.mrenann.cart.presentation.state.CartEvent
import br.mrenann.core.domain.model.Product
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartScreenModel(
    private val addUseCase: AddCartUseCase,
    private val clearUseCase: ClearCartUseCase,
    private val getUseCase: GetProductsFromCartUseCase,
) : StateScreenModel<CartScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val products: List<Product>) : State()
    }

    fun addProduct(product: Product) {
        event(CartEvent.AddProduct(product))
    }

    fun getProducts() {
        event(CartEvent.GetProducts)
    }

    fun clearCart() {
        event(CartEvent.ClearCart)
    }

    private fun event(event: CartEvent) {
        when (event) {
            is CartEvent.AddProduct -> {
                screenModelScope.launch {
                    addUseCase.invoke(Params(event.product))
                        .collectLatest { result -> }
                }
            }

            is CartEvent.GetProducts -> {
                screenModelScope.launch {
                    getUseCase.invoke().collectLatest { result ->
                        mutableState.value = Result(result)
                    }
                }
            }

            CartEvent.ClearCart -> {
                screenModelScope.launch {
                    clearUseCase.invoke().collectLatest { result ->
                        mutableState.value = Result(emptyList())
                    }
                }
            }
        }
    }


}