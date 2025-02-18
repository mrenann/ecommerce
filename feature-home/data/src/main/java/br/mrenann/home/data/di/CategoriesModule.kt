package br.mrenann.home.data.di

import br.mrenann.core.data.remote.StoreService
import br.mrenann.home.data.repository.CategoriesRepositoryImpl
import br.mrenann.home.data.source.CategoriesDataSourceImpl
import br.mrenann.home.data.usecase.CategoriesUseCaseImpl
import br.mrenann.home.domain.repository.CategoriesRepository
import br.mrenann.home.domain.source.CategoriesDataSource
import br.mrenann.home.domain.usecase.CategoriesUseCase
import org.koin.dsl.module

val categoriesModule = module {
    single<CategoriesDataSource> {
        CategoriesDataSourceImpl(
            service = get<StoreService>(),
        )
    }

    single<CategoriesRepository> {
        CategoriesRepositoryImpl(
            remoteDataSource = get<CategoriesDataSource>(),
        )
    }

    single<CategoriesUseCase> {
        CategoriesUseCaseImpl(
            repository = get<CategoriesRepository>(),
        )
    }
}