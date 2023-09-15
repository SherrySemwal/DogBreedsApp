package com.sherry.dogapp.repository

import com.sherry.dogapp.api.ApiResult
import com.sherry.dogapp.api.local.LocalDataSource
import com.sherry.dogapp.api.remote.RemoteDataSource
import com.sherry.dogapp.db.entity.DogBreedEntity
import com.sherry.dogapp.db.entity.toDogBreedEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class ImpDogBreedRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : DogBreedRepository {

    override fun getAllDogBreeds(): Flow<ApiResult<List<DogBreedEntity>, String>> =
        flow<ApiResult<List<DogBreedEntity>, String>> {
            localDataSource.getAllDogBreeds().collect { breeds ->
                if (breeds.isEmpty()) {
                    remoteDataSource.getAllDogBreeds()?.map { dogBreedDataModel ->
                        dogBreedDataModel.toDogBreedEntity()
                    }?.let { dogBreedEntityList ->
                        emit(ApiResult.OnSuccess(dogBreedEntityList))
                        localDataSource.insertAllDogBreedsInDb(dogBreedEntityList)
                    }
                } else {
                    emit(ApiResult.OnSuccess(breeds))
                }
            }
        }.flowOn(dispatcher)
            .catch {
                if (it is IOException) emit(ApiResult.NetworkError)
                else {
                    emit(ApiResult.OnFailure(it.message ?: ""))
                }
            }

    override fun getAllFavDogBreeds(): Flow<List<DogBreedEntity>> =
        localDataSource.getFavouriteDogBreedList()

    override suspend fun updateDogBreed(name: String, isFav: Boolean) {
        localDataSource.updateDogBreed(name, isFav)
    }
}
