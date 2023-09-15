package com.sherry.dogapp.ui.fav

import com.sherry.dogapp.db.entity.DogBreedEntity

sealed class FavDogBreedUiState {
    object Loading : FavDogBreedUiState()
    data class Success(val data: List<DogBreedEntity>) : FavDogBreedUiState()
}