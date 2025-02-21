package br.mrenann.productdetails.presentation.di

import br.mrenann.productdetails.domain.usecase.ProductUseCase
import br.mrenann.productdetails.presentation.screenModel.DetailsScreenModel
import org.koin.dsl.module

val detailsModule = module {
    factory {
        DetailsScreenModel(
            productUseCase = get<ProductUseCase>(),
            addToFavoriteUseCase = get(),
            removeFromFavoriteUseCase = get(),
            isFavoritedUseCase = get()
        )
    }
}