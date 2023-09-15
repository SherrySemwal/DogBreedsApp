package com.sherry.dogapp.repository

import com.sherry.dogapp.api.ApiResult
import com.sherry.dogapp.db.entity.DogBreedEntity
import kotlinx.coroutines.flow.Flow

interface DogBreedRepository {
    fun getAllDogBreeds(): Flow<ApiResult<List<DogBreedEntity>, String>>
    fun getAllFavDogBreeds(): Flow<List<DogBreedEntity>>
    suspend fun updateDogBreed(name: String, isFav: Boolean)
}