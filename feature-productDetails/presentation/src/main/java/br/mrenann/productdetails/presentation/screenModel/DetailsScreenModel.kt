package br.mrenann.productdetails.presentation.screenModel

import br.mrenann.core.domain.model.Product
import br.mrenann.productdetails.domain.usecase.AddToFavoriteUseCase
import br.mrenann.productdetails.domain.usecase.IsFavoritedUseCase
import br.mrenann.productdetails.domain.usecase.ProductUseCase
import br.mrenann.productdetails.domain.usecase.RemoveFromFavoriteUseCase
import br.mrenann.productdetails.presentation.state.DetailsEvent
import br.mrenann.productdetails.presentation.state.DetailsState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailsScreenModel(
    private val productUseCase: ProductUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val isFavoritedUseCase: IsFavoritedUseCase
) : StateScreenModel<DetailsScreenModel.State>(State.Init) {
    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val state: DetailsState) : State()
    }

    fun checkedFavorite(checkedFavorite: DetailsEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    fun getDetails(getDetails: DetailsEvent.GetDetails) {
        event(getDetails)
    }

    fun favorite(item: Product) {
        if (state.value is State.Result) {
            if ((state.value as State.Result).state.checked) {
                event(DetailsEvent.RemoveFavorite(item))
            } else {
                event(DetailsEvent.AddFavorite(item))
            }
        }

    }

    private fun event(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.AddFavorite -> {
                screenModelScope.launch {
                    addToFavoriteUseCase.invoke(
                        params = AddToFavoriteUseCase.Params(
                            product = event.product
                        )
                    ).collectLatest { result ->
                        val currentState = mutableState.value
                        if (currentState is State.Result) {
                            mutableState.value = State.Result(
                                currentState.state.copy(
                                    checked = true
                                )
                            )
                        }
                    }
                }
            }

            is DetailsEvent.CheckedFavorite -> {
                screenModelScope.launch {
                    isFavoritedUseCase.invoke(
                        params = IsFavoritedUseCase.Params(
                            id = event.id,
                        )
                    ).collectLatest { result ->
                        val currentState = mutableState.value
                        if (currentState is State.Result) {
                            mutableState.value = State.Result(
                                currentState.state.copy(
                                    checked = result
                                )
                            )
                        }
                    }
                }
            }

            is DetailsEvent.GetDetails -> {
                screenModelScope.launch {
                    productUseCase.invoke(
                        params = ProductUseCase.Params(
                            id = event.id
                        )
                    ).collect { resultData ->
                        val currentState = mutableState.value
                        if (currentState is State.Result) {
                            mutableState.value = State.Result(
                                currentState.state.copy(
                                    isLoading = false,
                                    product = resultData,
                                )
                            )
                        } else {
                            // If the state was not in Result before, set it directly
                            mutableState.value = State.Result(
                                DetailsState(
                                    isLoading = false,
                                    product = resultData,
                                )
                            )
                        }

                    }

                }
            }

            is DetailsEvent.RemoveFavorite -> {
                screenModelScope.launch {
                    removeFromFavoriteUseCase.invoke(
                        params = RemoveFromFavoriteUseCase.Params(
                            product = event.product
                        )
                    ).collectLatest { result ->
                        val currentState = mutableState.value
                        if (currentState is State.Result) {
                            mutableState.value = State.Result(
                                currentState.state.copy(
                                    checked = false
                                )
                            )
                        }
                    }
                }
            }
        }
    }


}