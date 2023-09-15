package com.sherry.dogapp.api.remote

import com.sherry.dogapp.model.DogBreedDataModel

interface RemoteDataSource {
    suspend fun getAllDogBreeds(): List<DogBreedDataModel>?
}