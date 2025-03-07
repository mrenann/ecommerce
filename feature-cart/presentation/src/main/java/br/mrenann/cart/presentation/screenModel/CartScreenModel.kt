package br.mrenann.cart.presentation.screenModel

import android.util.Log
import br.mrenann.cart.domain.usecase.AddCartUseCase
import br.mrenann.cart.domain.usecase.AddCartUseCase.Params
import br.mrenann.cart.domain.usecase.ApplyCouponUseCase
import br.mrenann.cart.domain.usecase.ClearCartUseCase
import br.mrenann.cart.domain.usecase.DecreaseUseCase
import br.mrenann.cart.domain.usecase.DeleteCartUseCase
import br.mrenann.cart.domain.usecase.GetCartTotalUseCase
import br.mrenann.cart.domain.usecase.GetProductsFromCartUseCase
import br.mrenann.cart.domain.usecase.IncreaseUseCase
import br.mrenann.cart.presentation.screenModel.CartScreenModel.State.Result
import br.mrenann.cart.presentation.state.CartEvent
import br.mrenann.cart.presentation.state.CartState
import br.mrenann.core.domain.model.ProductCart
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CartScreenModel(
    private val addUseCase: AddCartUseCase,
    private val removeUseCase: DeleteCartUseCase,
    private val clearUseCase: ClearCartUseCase,
    private val getUseCase: GetProductsFromCartUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase,
    private val getCartTotalUseCase: GetCartTotalUseCase,
    private val increaseUseCase: IncreaseUseCase,
    private val decreaseUseCase: DecreaseUseCase
) : StateScreenModel<CartScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val state: CartState) : State()
    }

    fun addProduct(product: ProductCart) {
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

    fun removeProduct(product: ProductCart) {
        event(CartEvent.RemoveProduct(product))
    }

    fun increaseQuantity(id: String) {
        event(CartEvent.increaseQuantity(id))
    }

    fun decreaseQuantity(id: String) {
        event(CartEvent.decreaseQuantity(id))
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
                    val cartTotal = getCartTotalUseCase.invoke().first()
                    getUseCase.invoke().collectLatest { result ->
                        mutableState.value = Result(
                            CartState(
                                products = result,
                                itemsCount = result.size,
                                total = cartTotal
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
                                            discountApplied = 0.0
                                        )
                                    )
                                }
                            }

                            is ApplyCouponUseCase.Result.Success -> {
                                Log.i("COUPON", "SUCCESS")

                                if (currentState is Result) {
                                    val updatedProducts =
                                        currentState.state.products.map { product ->
                                            val discountFactor =
                                                1 - (result.discountAmount / currentState.state.total)
                                            product.copy(priceFinal = product.price * discountFactor)
                                        }

                                    mutableState.value = Result(
                                        currentState.state.copy(
                                            products = updatedProducts,
                                            discountApplied = result.discountAmount,
                                            total = currentState.state.total - result.discountAmount
                                        )
                                    )
                                }
                            }

                        }

                    }
                }
            }

            is CartEvent.RemoveProduct -> {
                screenModelScope.launch {
                    removeUseCase.invoke(DeleteCartUseCase.Params(event.product))
                        .collectLatest { }
                }
            }

            is CartEvent.decreaseQuantity -> {
                screenModelScope.launch {
                    decreaseUseCase.invoke(DecreaseUseCase.Params(event.id))
                        .collectLatest { result -> }
                }
            }

            is CartEvent.increaseQuantity -> {
                screenModelScope.launch {
                    increaseUseCase.invoke(IncreaseUseCase.Params(event.id))
                        .collectLatest { result -> }
                }
            }
        }
    }


}