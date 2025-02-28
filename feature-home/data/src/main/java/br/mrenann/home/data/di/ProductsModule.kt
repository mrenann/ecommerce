package br.mrenann.home.data.di

import br.mrenann.core.data.remote.StoreService
import br.mrenann.home.data.repository.ProductsRepositoryImpl
import br.mrenann.home.data.source.ProductsDataSourceImpl
import br.mrenann.home.data.usecase.ProductsByCategoryUseCaseImpl
import br.mrenann.home.data.usecase.ProductsUseCaseImpl
import br.mrenann.home.domain.repository.ProductsRepository
import br.mrenann.home.domain.source.ProductsDataSource
import br.mrenann.home.domain.usecase.ProductsByCategoryUseCase
import br.mrenann.home.domain.usecase.ProductsUseCase
import org.koin.dsl.module

val productsModule = module {
    single<ProductsDataSource> {
        ProductsDataSourceImpl(
            service = get<StoreService>(),
        )
    }

    single<ProductsRepository> {
        ProductsRepositoryImpl(
            remoteDataSource = get<ProductsDataSource>(),
        )
    }

    single<ProductsUseCase> {
        ProductsUseCaseImpl(
            repository = get<ProductsRepository>(),
        )
    }
    single<ProductsByCategoryUseCase> {
        ProductsByCategoryUseCaseImpl(
            repository = get<ProductsRepository>(),
        )
    }
}