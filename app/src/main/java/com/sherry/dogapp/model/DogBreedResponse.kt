package com.sherry.dogapp.model

data class DogBreedResponse(
    val status: String,
    val message: Map<String, List<String>>
)