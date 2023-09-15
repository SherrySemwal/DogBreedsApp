package com.sherry.dogapp.model

data class DogBreedDataModel(
    val breedName: String,
    val subTypeCount: Int,
    val breedImageUrl: String?,
    var isFav: Boolean = false
)