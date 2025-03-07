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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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

//    init {
//        getProducts()
//    }

    fun setAddress(typeAddress: String, address: String) {
        val currentState = state.value
        if (currentState is Result) {
            mutableState.value = Result(
                currentState.state.copy(
                    typeAddress = typeAddress,
                    location = address
                )
            )
        }
    }

    fun resetCouponError() {
        val currentState = state.value
        if (currentState is Result) {
            mutableState.value = Result(
                currentState.state.copy(
                    couponError = null // Reset the coupon error
                )
            )
        }
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

    fun applyCoupon(code: String) {
        val userId = Firebase.auth.currentUser?.uid.toString()

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
        screenModelScope.launch {
            when (event) {
                is CartEvent.AddProduct -> {
                    addUseCase.invoke(Params(event.product)).collectLatest { }
                }

                is CartEvent.GetProducts -> {
                    getUseCase.invoke().collectLatest { products ->
                        currentSubtotal = products.sumOf { it.price * it.qtd }
                        val cartTotal = getCartTotalUseCase.invoke().first()
                        mutableState.value = Result(
                            CartState(
                                products = products,
                                itemsCount = products.size,
                                total = cartTotal,
                                couponError = null
                            )
                        )
                    }
                }

                CartEvent.ClearCart -> {
                    clearUseCase.invoke().collectLatest {
                        mutableState.value =
                            Result(CartState(products = emptyList(), itemsCount = 0))
                    }
                }

                CartEvent.RemoveCoupon -> {
                    clearCouponUseCase().collectLatest {
                        val currentState = state.value
                        if (currentState is Result) {
                            mutableState.value = Result(
                                currentState.state.copy(
                                    discountApplied = 0.0,
                                    total = currentSubtotal,
                                    couponError = null,
                                    couponCode = null
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
                    val products = getUseCase.invoke().first()
                    currentSubtotal = products.sumOf { it.price * it.qtd }

                    applyCouponUseCase(
                        ApplyCouponUseCase.Params(
                            userId = event.userId,
                            code = event.code,
                            subtotal = currentSubtotal
                        )
                    ).collectLatest { result ->
                        when (result) {
                            is ApplyCouponUseCase.Result.Invalid -> {
                                Log.i("COUPON", "INVALID")
                                Log.i("COUPON", "${result.reason}")
                                val currentState = state.value
                                if (currentState is Result) {
                                    mutableState.value = Result(
                                        currentState.state.copy(
                                            couponError = result.reason // Store the error reason
                                        )
                                    )
                                }

                            }

                            is ApplyCouponUseCase.Result.Success -> {
                                Log.i("COUPON", "SUCCESS")
                                val currentState = state.value
                                if (currentState is Result) {
                                    mutableState.value = Result(
                                        currentState.state.copy(
                                            discountApplied = result.discountAmount,
                                            total = currentSubtotal - result.discountAmount,
                                            couponCode = event.code,
                                            couponError = null
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

                is CartEvent.increaseQuantity -> {
                    increaseUseCase.invoke(IncreaseUseCase.Params(event.id)).collectLatest {
                        reapplyCouponIfNeeded()
                    }
                }

                is CartEvent.decreaseQuantity -> {
                    decreaseUseCase.invoke(DecreaseUseCase.Params(event.id)).collectLatest {
                        reapplyCouponIfNeeded()
                    }
                }
            }
        }
    }

    private fun reapplyCouponIfNeeded() {
        val currentState = state.value
        if (currentState is Result && currentState.state.discountApplied > 0) {
            val couponCode = currentState.state.couponCode
            if (couponCode != null) {
                applyCoupon(couponCode)
            }
        }
    }
}