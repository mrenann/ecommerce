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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class DetailsScreenModel(
    private val productUseCase: ProductUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val isFavoritedUseCase: IsFavoritedUseCase
) : StateScreenModel<DetailsScreenModel.State>(State.Init) {
    sealed class State {
        data object Init : State()
        data object Loading : State()
        data class Result(val state: DetailsState) : State()
        data class Error(val message: String) : State()
    }

    fun checkedFavorite(checkedFavorite: DetailsEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    fun getDetails(getDetails: DetailsEvent.GetDetails) {
        event(getDetails)
    }

    fun favorite(item: Product) {
        val currentState = mutableState.value
        if (currentState is State.Result) {
            val isChecked = currentState.state.checked
            mutableState.value = State.Result(currentState.state.copy(checked = !isChecked))

            if (isChecked) {
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
                    ).collect { result ->
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
                    val productFlow = productUseCase.invoke(
                        params = ProductUseCase.Params(id = event.id)
                    )

                    val isFavoritedFlow = isFavoritedUseCase.invoke(
                        params = IsFavoritedUseCase.Params(id = event.id)
                    )

                    combine(productFlow, isFavoritedFlow) { resultData, isFavorited ->
                        if (resultData.isSuccess) {
                            val currentState = mutableState.value
                            if (currentState is State.Result) {
                                mutableState.value = State.Result(
                                    currentState.state.copy(
                                        isLoading = false,
                                        product = resultData.getOrNull(),
                                        checked = isFavorited
                                    )
                                )
                            } else {
                                // If the state was not in Result before, set it directly
                                mutableState.value = State.Result(
                                    DetailsState(
                                        isLoading = false,
                                        product = resultData.getOrNull(),
                                        checked = isFavorited
                                    )
                                )
                            }
                        } else if (resultData.isFailure) {
                            mutableState.value = State.Error(
                                resultData.exceptionOrNull().toString()
                            )
                        }
                    }.collectLatest { }


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