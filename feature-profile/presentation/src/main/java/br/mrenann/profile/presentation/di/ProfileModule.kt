package br.mrenann.profile.presentation.di

import br.mrenann.profile.presentation.screenModel.OrdersScreenModel
import org.koin.dsl.module

val profileModule = module {
    factory {
        OrdersScreenModel()
    }
}