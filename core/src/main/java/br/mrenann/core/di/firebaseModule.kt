package br.mrenann.core.di

import br.mrenann.core.data.firestore.FirebaseService
import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseService() }
    single { FavoritesFirestoreRepository(get()) }
}
