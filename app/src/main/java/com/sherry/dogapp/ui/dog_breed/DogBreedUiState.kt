package com.sherry.dogapp.ui.dog_breed

import com.sherry.dogapp.db.entity.DogBreedEntity

sealed class DogBreedUiState {
    object Loading : DogBreedUiState()
    data class Success(val data: List<DogBreedEntity>) : DogBreedUiState()
    data class Error(val error: String) : DogBreedUiState()
}