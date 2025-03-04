package br.mrenann.favorites.presentation.screenModel

import br.mrenann.favorites.domain.usecase.AddFavoriteUseCase
import br.mrenann.favorites.domain.usecase.GetFavoritesUseCase
import br.mrenann.favorites.presentation.state.FavoriteState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteScreenModel(
    private val addUseCase: AddFavoriteUseCase,
    private val getUseCase: GetFavoritesUseCase
) : StateScreenModel<FavoriteScreenModel.State>(State.Loading) {
    sealed class State {
        data object Loading : State()
        data class Result(val state: FavoriteState) : State()
    }

    init {
        fetch()

    }

    fun fetch() {
        screenModelScope.launch {
            mutableState.value = State.Loading
            getUseCase.invoke().collectLatest { products ->
                mutableState.value = State.Result(
                    state = FavoriteState(
                        products = products
                    )
                )
            }
        }
    }

}