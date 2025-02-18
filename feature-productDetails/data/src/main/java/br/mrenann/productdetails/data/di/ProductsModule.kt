package br.mrenann.productdetails.data.di

import br.mrenann.core.data.remote.StoreService
import br.mrenann.productdetails.data.repository.ProductRepositoryImpl
import br.mrenann.productdetails.data.source.ProductDataSourceImpl
import br.mrenann.productdetails.data.usecase.ProductUseCaseImpl
import br.mrenann.productdetails.domain.repository.ProductRepository
import br.mrenann.productdetails.domain.source.ProductDataSource
import br.mrenann.productdetails.domain.usecase.ProductUseCase
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
}