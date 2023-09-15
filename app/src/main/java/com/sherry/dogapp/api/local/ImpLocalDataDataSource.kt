package com.sherry.dogapp.api.local

import com.sherry.dogapp.db.dao.DogBreedsDao
import com.sherry.dogapp.db.entity.DogBreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImpLocalDataDataSource @Inject constructor(private val dao: DogBreedsDao) : LocalDataSource {

    override fun getAllDogBreeds(): Flow<List<DogBreedEntity>> {
        return dao.getDogBreedList()
    }

    override suspend fun insertAllDogBreedsInDb(dogBreeds: List<DogBreedEntity>) {
        dao.insertAllDogBreeds(dogBreeds)
    }

    override suspend fun updateDogBreed(name: String, isFav: Boolean) {
        dao.updateDogBreed(name, isFav)
    }

    override fun getFavouriteDogBreedList(): Flow<List<DogBreedEntity>> {
        return dao.getFavouriteDogBreeds()
    }
}