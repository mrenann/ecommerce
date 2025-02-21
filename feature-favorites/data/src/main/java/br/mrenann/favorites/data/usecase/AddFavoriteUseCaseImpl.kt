package br.mrenann.favorites.data.usecase

import br.mrenann.core.data.firestore.repository.FavoritesFirestoreRepository
import br.mrenann.favorites.data.mapper.toMap
import br.mrenann.favorites.domain.usecase.AddFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddFavoriteUseCaseImpl(
    private val repository: FavoritesFirestoreRepository
) : AddFavoriteUseCase {
    override suspend fun invoke(params: AddFavoriteUseCase.Params): Flow<Unit> {
        return flow {
            val insert = repository.addFavorite(
                userId = "1",
                productId = params.item.id.toString(),
                productData = params.item.toMap()
            )
            emit(insert)
        }.flowOn(Dispatchers.IO)
    }

}