package br.mrenann.favorites.data.di

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.favorites.data.usecase.AddFavoriteUseCaseImpl
import br.mrenann.favorites.domain.usecase.AddFavoriteUseCase
import org.koin.dsl.module

val favoriteModule = module {

    single<AddFavoriteUseCase> { AddFavoriteUseCaseImpl(get<FavoritesFirestoreRepository>()) }

}