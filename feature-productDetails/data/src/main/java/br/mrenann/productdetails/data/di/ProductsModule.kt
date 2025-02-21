package br.mrenann.productdetails.data.di

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.core.data.remote.StoreService
import br.mrenann.productdetails.data.repository.ProductRepositoryImpl
import br.mrenann.productdetails.data.source.ProductDataSourceImpl
import br.mrenann.productdetails.data.usecase.AddToFavoriteUseCaseImpl
import br.mrenann.productdetails.data.usecase.GetFavoritesUseCaseImpl
import br.mrenann.productdetails.data.usecase.IsFavoritedUseCaseImpl
import br.mrenann.productdetails.data.usecase.ProductUseCaseImpl
import br.mrenann.productdetails.data.usecase.RemoveFromFavoriteUseCaseImpl
import br.mrenann.productdetails.domain.repository.ProductRepository
import br.mrenann.productdetails.domain.source.ProductDataSource
import br.mrenann.productdetails.domain.usecase.AddToFavoriteUseCase
import br.mrenann.productdetails.domain.usecase.GetFavoritesUseCase
import br.mrenann.productdetails.domain.usecase.IsFavoritedUseCase
import br.mrenann.productdetails.domain.usecase.ProductUseCase
import br.mrenann.productdetails.domain.usecase.RemoveFromFavoriteUseCase
import org.koin.dsl.module

val productModule = module {
    single<ProductDataSource> {
        ProductDataSourceImpl(
            service = get<StoreService>(),
        )
    }

    single<ProductRepository> {
        ProductRepositoryImpl(
            remoteDataSource = get<ProductDataSource>(),
        )
    }

    single<ProductUseCase> {
        ProductUseCaseImpl(
            repository = get<ProductRepository>(),
        )
    }

    single<AddToFavoriteUseCase> {
        AddToFavoriteUseCaseImpl(
            repository = get<FavoritesFirestoreRepository>(),
        )
    }

    single<GetFavoritesUseCase> {
        GetFavoritesUseCaseImpl(
            repository = get<FavoritesFirestoreRepository>(),
        )
    }

    single<IsFavoritedUseCase> {
        IsFavoritedUseCaseImpl(
            repository = get<FavoritesFirestoreRepository>(),
        )
    }

    single<RemoveFromFavoriteUseCase> {
        RemoveFromFavoriteUseCaseImpl(
            repository = get<FavoritesFirestoreRepository>(),
        )
    }


}