package com.sherry.dogapp.ui.dog_breed

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sherry.dogapp.api.ApiResult
import com.sherry.dogapp.repository.DogBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DogBreedViewModel @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : ViewModel() {
    private val _dogBreedListUiState: MutableStateFlow<DogBreedUiState> =
        MutableStateFlow(DogBreedUiState.Loading)
    internal val dogBreedListUiState: StateFlow<DogBreedUiState> = _dogBreedListUiState

    init {
        getAllDogBreedData()
    }

    @VisibleForTesting
    internal fun getAllDogBreedData() {
        viewModelScope.launch {
            dogBreedRepository.getAllDogBreeds().collect {
                when (it) {
                    is ApiResult.OnSuccess -> {
                        _dogBreedListUiState.value = DogBreedUiState.Success(it.response)
                    }
                    is ApiResult.OnFailure -> {
                        _dogBreedListUiState.value = DogBreedUiState.Error(it.exception)
                    }
                    is ApiResult.NetworkError -> {
                        _dogBreedListUiState.value = DogBreedUiState.Error("No Internet Connection")
                    }
                }
            }
        }
    }

    internal fun addDogBreedInFav(name: String, isFav: Boolean){
        viewModelScope.launch {
            dogBreedRepository.updateDogBreed(name, isFav)
        }
    }
}