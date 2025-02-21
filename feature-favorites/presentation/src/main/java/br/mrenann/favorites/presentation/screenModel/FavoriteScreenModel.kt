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
) : StateScreenModel<FavoriteScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val state: FavoriteState) : State()
    }

    init {
        fetch()

    }

    private fun fetch() {
        screenModelScope.launch {
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