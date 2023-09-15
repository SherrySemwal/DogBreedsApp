package com.sherry.dogapp.ui.fav

import com.sherry.dogapp.CoroutinesDispatcherTestRule
import com.sherry.dogapp.api.ApiResult
import com.sherry.dogapp.db.entity.DogBreedEntity
import com.sherry.dogapp.repository.DogBreedRepository
import com.sherry.dogapp.ui.dog_breed.DogBreedUiState
import com.sherry.dogapp.ui.dog_breed.DogBreedViewModel
import com.sherry.dogapp.ui.fav.FavDogBreedUiState
import com.sherry.dogapp.ui.fav.FavDogBreedViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavDogBreedViewModelTest {
    @get:Rule
    val coroutinesRule = CoroutinesDispatcherTestRule()

    private val dogBreedRepository: DogBreedRepository = mockk()
    private lateinit var classToTest: FavDogBreedViewModel

    @Test
    fun `getAllFavDogBreeds should return data`() = runTest {
        val dogBreedList = listOf(
            DogBreedEntity(
                breedName = "bulldog",
                subTypeCount = 2,
                breedImageUrl = "https://images.dog.ceo/breeds/bulldog-boston/n02096585_9122.jpg",
                isFav = true
            )
        )
        every { dogBreedRepository.getAllFavDogBreeds() } returns flow {
            emit(dogBreedList)
        }
        classToTest = FavDogBreedViewModel(dogBreedRepository)

        Assert.assertEquals(
            FavDogBreedUiState.Success(dogBreedList),
            classToTest.favDogBreedUiState.value
        )
    }

    @Test
    fun `deleteDogBreedFromFav should call updateDogBreed`() = runTest {
        val breedName = "bulldog"
        val isFav = false
        every { dogBreedRepository.getAllFavDogBreeds() } returns flow { mockk() }
        coEvery { dogBreedRepository.updateDogBreed(breedName, isFav) } returns Unit

        classToTest = FavDogBreedViewModel(dogBreedRepository)
        classToTest.deleteDogBreedFromFav(breedName)

        coVerify { dogBreedRepository.updateDogBreed(breedName, isFav) }
    }
}