package br.mrenann.cart.presentation.screenModel

import android.util.Log
import br.mrenann.cart.domain.usecase.AddCartUseCase
import br.mrenann.cart.domain.usecase.AddCartUseCase.Params
import br.mrenann.cart.domain.usecase.ApplyCouponUseCase
import br.mrenann.cart.domain.usecase.ClearCartUseCase
import br.mrenann.cart.domain.usecase.ClearCouponUseCase
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
    private val decreaseUseCase: DecreaseUseCase,
    private val clearCouponUseCase: ClearCouponUseCase
) : StateScreenModel<CartScreenModel.State>(State.Init) {

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val state: CartState) : State()
    }

    private var currentSubtotal: Double = 0.0

    init {
        getProducts()
    }

    fun removeCoupon() { // Add removeCoupon function
        event(CartEvent.RemoveCoupon)
    }

    fun addProduct(product: ProductCart) {
        event(CartEvent.AddProduct(product))
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

    fun applyCoupon(userId: String, code: String) {
        // Use the stored subtotal
        event(CartEvent.ApplyCoupon(userId, code, currentSubtotal))
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
        screenModelScope.launch { // All events now launch coroutines
            when (event) {
                is CartEvent.AddProduct -> {
                    addUseCase.invoke(Params(event.product)).collectLatest { }
                }

                is CartEvent.GetProducts -> {
                    getUseCase.invoke().collectLatest { products ->
                        // Recalculate subtotal whenever products change
                        currentSubtotal = products.sumOf { it.price * it.qtd }
                        val cartTotal = getCartTotalUseCase.invoke().first()
                        mutableState.value = Result(
                            CartState(
                                products = products,
                                itemsCount = products.size,
                                total = cartTotal
                            )
                        ) //Pass cart total here
                    }
                }

                CartEvent.ClearCart -> {
                    clearUseCase.invoke().collectLatest {
                        mutableState.value =
                            Result(CartState(products = emptyList(), itemsCount = 0))
                    }
                }

                CartEvent.RemoveCoupon -> { // Handle RemoveCoupon event
                    clearCouponUseCase().collectLatest {
                        // Reset discountApplied and update the total
                        val currentState = state.value
                        if (currentState is Result) {
                            mutableState.value = Result(
                                currentState.state.copy(
                                    discountApplied = 0.0,
                                    total = currentSubtotal // Reset to subtotal
                                )
                            )
                        }
                    }
                }

                CartEvent.CountItems -> {
                    getUseCase.invoke().collectLatest { products ->
                        mutableState.value =
                            Result(CartState(products = emptyList(), itemsCount = products.size))
                    }
                }

                is CartEvent.ApplyCoupon -> {
                    applyCouponUseCase(
                        ApplyCouponUseCase.Params(
                            userId = event.userId,
                            code = event.code,
                            subtotal = event.subtotal
                        )
                    ).collectLatest { result ->
                        when (result) {
                            is ApplyCouponUseCase.Result.Invalid -> {
                                Log.i("COUPON", "INVALID")
                                //  Could add an error message state here
                            }

                            is ApplyCouponUseCase.Result.Success -> {
                                Log.i("COUPON", "SUCCESS")
                                // Update the UI state to reflect the applied coupon
                                // We no longer need to modify individual product prices.
                                val currentState = state.value
                                if (currentState is Result) {
                                    mutableState.value = Result(
                                        currentState.state.copy(
                                            discountApplied = result.discountAmount, // Store discount
                                            total = currentSubtotal - result.discountAmount//update the total.
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                is CartEvent.RemoveProduct -> {
                    removeUseCase.invoke(DeleteCartUseCase.Params(event.product)).collectLatest {}
                }

                is CartEvent.decreaseQuantity -> {
                    decreaseUseCase.invoke(DecreaseUseCase.Params(event.id)).collectLatest { }
                }

                is CartEvent.increaseQuantity -> {
                    increaseUseCase.invoke(IncreaseUseCase.Params(event.id)).collectLatest { }
                }
            }
        }
    }
}