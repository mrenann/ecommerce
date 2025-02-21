package br.mrenann.favorites.presentation.screenModel

import br.mrenann.core.domain.model.Product
import br.mrenann.favorites.domain.usecase.AddFavoriteUseCase
import br.mrenann.favorites.domain.usecase.AddFavoriteUseCase.Params
import br.mrenann.favorites.presentation.state.FavoriteEvent
import br.mrenann.favorites.presentation.state.FavoriteState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteScreenModel(
    private val addUseCase: AddFavoriteUseCase,
) : StateScreenModel<FavoriteScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val state: FavoriteState) : State()
    }

    fun addProduct(product: Product) {
        event(FavoriteEvent.AddProduct(product))
    }


    private fun event(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddProduct -> {
                screenModelScope.launch {
                    addUseCase.invoke(Params(event.product))
                        .collectLatest { result -> }
                }
            }

        }
    }


}