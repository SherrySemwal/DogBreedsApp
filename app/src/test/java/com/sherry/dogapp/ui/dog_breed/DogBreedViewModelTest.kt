package com.sherry.dogapp.ui.dog_breed

import com.sherry.dogapp.CoroutinesDispatcherTestRule
import com.sherry.dogapp.api.ApiResult
import com.sherry.dogapp.db.entity.DogBreedEntity
import com.sherry.dogapp.repository.DogBreedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DogBreedViewModelTest {
    @get:Rule
    val coroutinesRule = CoroutinesDispatcherTestRule()

    private val dogBreedRepository: DogBreedRepository = mockk()
    private lateinit var classToTest: DogBreedViewModel

    @Test
    fun `getAllDogBreeds should return on success`() = runTest {
        val dogBreedList = listOf(
            DogBreedEntity(
                breedName = "bulldog",
                subTypeCount = 2,
                breedImageUrl = "https://images.dog.ceo/breeds/bulldog-boston/n02096585_9122.jpg"
            )
        )
        every { dogBreedRepository.getAllDogBreeds() } returns flow {
            emit(ApiResult.OnSuccess(dogBreedList))
        }
        classToTest = DogBreedViewModel(dogBreedRepository)

        Assert.assertEquals(
            DogBreedUiState.Success(dogBreedList),
            classToTest.dogBreedListUiState.value
        )
    }

    @Test
    fun `getAllDogBreeds should return error`() = runTest {
        coEvery { dogBreedRepository.getAllDogBreeds() } returns flow {
            emit(ApiResult.OnFailure("Error"))
        }
        classToTest = DogBreedViewModel(dogBreedRepository)

        Assert.assertEquals(
            DogBreedUiState.Error("Error"),
            classToTest.dogBreedListUiState.value
        )
    }

    @Test
    fun `getAllDogBreeds should return network error`() = runTest {
        coEvery { dogBreedRepository.getAllDogBreeds() } returns flow {
            emit(ApiResult.NetworkError)
        }
        classToTest = DogBreedViewModel(dogBreedRepository)

        Assert.assertEquals(
            DogBreedUiState.Error("No Internet Connection"),
            classToTest.dogBreedListUiState.value
        )
    }

    @Test
    fun `addDogBreedInFav should call updateDogBreed`() = runTest {
        val breedName = "bulldog"
        val isFav = true
        every { dogBreedRepository.getAllDogBreeds() } returns flow { mockk() }
        coEvery { dogBreedRepository.updateDogBreed(breedName, isFav) } returns Unit

        classToTest = DogBreedViewModel(dogBreedRepository)
        classToTest.addDogBreedInFav(breedName, isFav)

        coVerify { dogBreedRepository.updateDogBreed(breedName, isFav) }
    }

}