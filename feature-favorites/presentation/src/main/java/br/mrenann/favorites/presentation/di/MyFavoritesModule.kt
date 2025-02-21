package br.mrenann.favorites.presentation.di

import br.mrenann.favorites.domain.usecase.AddFavoriteUseCase
import br.mrenann.favorites.domain.usecase.GetFavoritesUseCase
import br.mrenann.favorites.presentation.screenModel.FavoriteScreenModel
import org.koin.dsl.module

val myFavoritesModule = module {
    factory {
        FavoriteScreenModel(
            get<AddFavoriteUseCase>(),
            get<GetFavoritesUseCase>()
        )
    }
}