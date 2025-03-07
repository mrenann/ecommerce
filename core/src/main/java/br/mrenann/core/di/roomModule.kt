package br.mrenann.core.di

import androidx.room.Room
import br.mrenann.core.data.local.dao.AppliedCouponDao
import br.mrenann.core.data.local.dao.CartDao
import br.mrenann.core.data.local.dao.FavoritesDao
import br.mrenann.core.data.local.database.StoreDatabase
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(get(), StoreDatabase::class.java, "store_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<CartDao> {
        val database = get<StoreDatabase>()
        database.cartDao()
    }

    single<FavoritesDao> {
        val database = get<StoreDatabase>()
        database.favoritesDao()
    }

    single<AppliedCouponDao> {
        val database = get<StoreDatabase>()
        database.appliedCouponDao()
    }

}