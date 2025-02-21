package br.mrenann.cart.presentation.screenModel

import android.util.Log
import br.mrenann.cart.domain.usecase.AddCartUseCase
import br.mrenann.cart.domain.usecase.AddCartUseCase.Params
import br.mrenann.cart.domain.usecase.ApplyCouponUseCase
import br.mrenann.cart.domain.usecase.ClearCartUseCase
import br.mrenann.cart.domain.usecase.GetProductsFromCartUseCase
import br.mrenann.cart.presentation.screenModel.CartScreenModel.State.Result
import br.mrenann.cart.presentation.state.CartEvent
import br.mrenann.cart.presentation.state.CartState
import br.mrenann.core.domain.model.Product
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartScreenModel(
    private val addUseCase: AddCartUseCase,
    private val clearUseCase: ClearCartUseCase,
    private val getUseCase: GetProductsFromCartUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase
) : StateScreenModel<CartScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val state: CartState) : State()
    }

    fun addProduct(product: Product) {
        event(CartEvent.AddProduct(product))
    }

    init {
        getProducts()
    }

    fun getProducts() {
        event(CartEvent.GetProducts)
    }

    fun clearCart() {
        event(CartEvent.ClearCart)
    }

    fun countItemsFromCart() {
        event(CartEvent.CountItems)
    }

    fun applyCoupon(userId: String, code: String, subtotal: Double) {
        event(CartEvent.ApplyCoupon(userId, code, subtotal))
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
                        mutableState.value = Result(
                            CartState(
                                products = result,
                                itemsCount = result.size
                            )
                        )
                    }
                }
            }

            CartEvent.ClearCart -> {
                screenModelScope.launch {
                    clearUseCase.invoke().collectLatest {
                        mutableState.value = Result(
                            CartState(
                                products = emptyList(),
                                itemsCount = 0
                            )
                        )
                    }
                }
            }

            CartEvent.CountItems -> {
                screenModelScope.launch {
                    getUseCase.invoke().collectLatest { result ->
                        mutableState.value = Result(
                            CartState(
                                products = emptyList(),
                                itemsCount = result.size
                            )
                        )
                    }
                }
            }

            is CartEvent.ApplyCoupon -> {
                screenModelScope.launch {
                    applyCouponUseCase.invoke(
                        ApplyCouponUseCase.Params(
                            userId = event.userId,
                            code = event.code,
                            subtotal = event.subtotal
                        )
                    ).collectLatest { result ->
                        val currentState = mutableState.value


                        when (result) {
                            is ApplyCouponUseCase.Result.Invalid -> {
                                Log.i("COUPON", "INVALID")
                                if (currentState is Result) {
                                    mutableState.value = Result(
                                        currentState.state.copy(
                                            discountApplied = false
                                        )
                                    )
                                }
                            }

                            is ApplyCouponUseCase.Result.Success -> {
                                Log.i("COUPON", "SUCCESS")

                                if (currentState is Result) {
                                    mutableState.value = Result(
                                        currentState.state.copy(
                                            discountApplied = true
                                        )
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