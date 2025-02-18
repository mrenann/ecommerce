package br.mrenann.home.presentation.di

import br.mrenann.home.domain.usecase.CategoriesUseCase
import br.mrenann.home.domain.usecase.ProductsUseCase
import br.mrenann.home.presentation.screenModel.HomeScreenModel
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeScreenModel(
            productsUseCase = get<ProductsUseCase>(),
            categoriesUseCase = get<CategoriesUseCase>()
        )
    }
}