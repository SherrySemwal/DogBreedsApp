package com.sherry.dogapp.api.remote

import com.sherry.dogapp.api.ApiInterface
import com.sherry.dogapp.model.DogBreedDataModel
import com.sherry.dogapp.model.DogBreedImageResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ImpRemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface,
) : RemoteDataSource {

    override suspend fun getAllDogBreeds(): List<DogBreedDataModel>?{
        try {
            val response = apiInterface.getAllDogBreeds()
            if (response.isSuccessful) {
                val dogBreedModelList = response.body()?.message?.let { createDogBreedModelList(it) }
                return dogBreedModelList?.sortedBy { it.breedName }
            } else {
                throw Exception(response.message())
            }
        }catch (exception: Exception){
            throw exception
        }
    }

    private suspend fun getDogBreedImage(breedName: String): DogBreedImageResponse? = try {
        apiInterface.getDogBreedImage(breedName).body()
    } catch (e: Exception) {
        throw e
    }

    private suspend fun createDogBreedModelList(breedMap: Map<String, List<String>>): List<DogBreedDataModel> {
        val breedList = mutableListOf<DogBreedDataModel>()
        coroutineScope {
            breedMap.keys.map { breedName ->
                async {
                    val imagePath = getDogBreedImage(breedName)?.message
                    val subBreedTypeCount = breedMap[breedName]?.size ?: 0
                    breedList.add(DogBreedDataModel(breedName, subBreedTypeCount, imagePath))
                }
            }.awaitAll()
        }
        return breedList
    }
}