package br.mrenann.profile.presentation.screenModel

import br.mrenann.core.domain.model.Order
import br.mrenann.profile.presentation.state.OrderState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrdersScreenModel : StateScreenModel<OrdersScreenModel.State>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Result(val state: OrderState) : State()
    }

    init {
        getOrders()
    }

    fun getOrders() {
        screenModelScope.launch {
            val db = Firebase.firestore
            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) {
                val orderRef =
                    db.collection("users").document(userId).collection("orders").get().await()
                val myOrders = orderRef.documents.mapNotNull {
                    it.toObject(Order::class.java)
                }.sortedByDescending { it.createdAt }

                mutableState.value = State.Result(OrderState(myOrders))

            }


        }

    }


}