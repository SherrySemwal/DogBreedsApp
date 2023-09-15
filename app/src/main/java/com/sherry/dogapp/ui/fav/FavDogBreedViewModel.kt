package com.sherry.dogapp.ui.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sherry.dogapp.repository.DogBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavDogBreedViewModel @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
): ViewModel() {

    private var _favDogBreedUiState: MutableStateFlow<FavDogBreedUiState> = MutableStateFlow(FavDogBreedUiState.Loading)
    val favDogBreedUiState: StateFlow<FavDogBreedUiState> = _favDogBreedUiState

    internal var removedPosition = -1

    init {
        getAllFavDogBreeds()
    }

    private fun getAllFavDogBreeds() {
        viewModelScope.launch {
            dogBreedRepository.getAllFavDogBreeds().collect { favList ->
                _favDogBreedUiState.value = FavDogBreedUiState.Success(favList)
            }
        }
    }

    fun deleteDogBreedFromFav(name: String){
        viewModelScope.launch {
            dogBreedRepository.updateDogBreed(name, false)
        }
    }
}