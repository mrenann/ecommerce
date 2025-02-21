package br.mrenann.cart.data.di

import br.mrenann.cart.data.repository.CartRepositoryImpl
import br.mrenann.cart.data.source.CartDataSourceImpl
import br.mrenann.cart.data.usecase.AddCartUseCaseImpl
import br.mrenann.cart.data.usecase.ApplyCouponUseCaseImpl
import br.mrenann.cart.data.usecase.ClearCartUseCaseImpl
import br.mrenann.cart.data.usecase.GetProductsFromCartUseCaseImpl
import br.mrenann.cart.domain.repository.CartRepository
import br.mrenann.cart.domain.source.CartDataSource
import br.mrenann.cart.domain.usecase.AddCartUseCase
import br.mrenann.cart.domain.usecase.ApplyCouponUseCase
import br.mrenann.cart.domain.usecase.ClearCartUseCase
import br.mrenann.cart.domain.usecase.GetProductsFromCartUseCase
import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import org.koin.dsl.module

val cartModule = module {

    single<CartDataSource> {
        CartDataSourceImpl(
            dao = get()
        )
    }

    single<CartRepository> {
        CartRepositoryImpl(
            dataSource = get()
        )
    }

    single<GetProductsFromCartUseCase> { GetProductsFromCartUseCaseImpl(get<CartRepository>()) }
    single<AddCartUseCase> { AddCartUseCaseImpl(get<CartRepository>()) }
    single<ClearCartUseCase> { ClearCartUseCaseImpl(get<CartRepository>()) }
    single<ApplyCouponUseCase> { ApplyCouponUseCaseImpl(get<FavoritesFirestoreRepository>()) }

}