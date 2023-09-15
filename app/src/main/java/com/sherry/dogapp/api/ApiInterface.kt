package com.sherry.dogapp.api

import com.sherry.dogapp.model.DogBreedImageResponse
import com.sherry.dogapp.model.DogBreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("breeds/list/all")
    suspend fun getAllDogBreeds(): Response<DogBreedResponse>

    @GET("breed/{breed_name}/images/random")
    suspend fun getDogBreedImage(@Path("breed_name") breedName: String): Response<DogBreedImageResponse>
}