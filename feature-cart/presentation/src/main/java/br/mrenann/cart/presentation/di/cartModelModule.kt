package br.mrenann.cart.presentation.di

import br.mrenann.cart.domain.usecase.AddCartUseCase
import br.mrenann.cart.domain.usecase.ApplyCouponUseCase
import br.mrenann.cart.domain.usecase.ClearCartUseCase
import br.mrenann.cart.domain.usecase.DecreaseUseCase
import br.mrenann.cart.domain.usecase.DeleteCartUseCase
import br.mrenann.cart.domain.usecase.GetCartTotalUseCase
import br.mrenann.cart.domain.usecase.GetProductsFromCartUseCase
import br.mrenann.cart.domain.usecase.IncreaseUseCase
import br.mrenann.cart.presentation.screenModel.CartScreenModel
import org.koin.dsl.module

val cartModelModule = module {
    factory {
        CartScreenModel(
            get<AddCartUseCase>(),
            get<DeleteCartUseCase>(),
            get<ClearCartUseCase>(),
            get<GetProductsFromCartUseCase>(),
            get<ApplyCouponUseCase>(),
            get<GetCartTotalUseCase>(),
            get<IncreaseUseCase>(),
            get<DecreaseUseCase>()

        )
    }
}