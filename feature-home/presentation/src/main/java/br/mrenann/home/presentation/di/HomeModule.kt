package br.mrenann.home.presentation.di

import br.mrenann.home.domain.usecase.CategoriesUseCase
import br.mrenann.home.domain.usecase.ProductsByCategoryUseCase
import br.mrenann.home.domain.usecase.ProductsUseCase
import br.mrenann.home.domain.usecase.SearchProductsUseCase
import br.mrenann.home.presentation.screenModel.CategoryScreenModel
import br.mrenann.home.presentation.screenModel.HomeScreenModel
import br.mrenann.home.presentation.screenModel.SearchScreenModel
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeScreenModel(
            productsUseCase = get<ProductsUseCase>(),
            categoriesUseCase = get<CategoriesUseCase>()
        )
    }

    factory {
        CategoryScreenModel(
            productsUseCase = get<ProductsByCategoryUseCase>()
        )
    }

    factory {
        SearchScreenModel(
            searchUseCae = get<SearchProductsUseCase>()
        )
    }
}