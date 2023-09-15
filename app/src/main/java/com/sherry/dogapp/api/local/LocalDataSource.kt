package com.sherry.dogapp.api.local

import com.sherry.dogapp.db.entity.DogBreedEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllDogBreeds(): Flow<List<DogBreedEntity>>
    suspend fun insertAllDogBreedsInDb(dogBreeds: List<DogBreedEntity>)
    suspend fun updateDogBreed(name: String, isFav: Boolean)
    fun getFavouriteDogBreedList(): Flow<List<DogBreedEntity>>
}