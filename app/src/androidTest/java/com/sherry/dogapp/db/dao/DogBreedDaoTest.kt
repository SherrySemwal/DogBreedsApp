package com.sherry.dogapp.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sherry.dogapp.db.DogBreedDatabase
import com.sherry.dogapp.db.entity.DogBreedEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class DogBreedDaoTest {

    private lateinit var database: DogBreedDatabase
    private lateinit var dogBreedsDao: DogBreedsDao

    @Before
    fun setUpDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DogBreedDatabase::class.java
        ).allowMainThreadQueries().build()

        dogBreedsDao = database.dogBreedsDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun check_if_insertAllDogBreeds_return_data() = runTest {
        val dogBreedList = listOf(
            DogBreedEntity(
                breedName = "bulldog",
                subTypeCount = 2,
                breedImageUrl = "https://images.dog.ceo/breeds/bulldog-boston/n02096585_9122.jpg"
            )
        )
        dogBreedsDao.insertAllDogBreeds(dogBreedList)
        val list: List<DogBreedEntity> = dogBreedsDao.getDogBreedList().first()
        Assert.assertEquals(dogBreedList[0].breedName, list[0].breedName)
    }

    @Test
    fun updateDogBreed_should_update_breed_fav_data() = runTest {
        val dogBreedList = listOf(
            DogBreedEntity(
                breedName = "bulldog",
                subTypeCount = 2,
                breedImageUrl = "https://images.dog.ceo/breeds/bulldog-boston/n02096585_9122.jpg"
            )
        )
        dogBreedsDao.insertAllDogBreeds(dogBreedList)
        val favListBeforeUpdate: List<DogBreedEntity> = dogBreedsDao.getFavouriteDogBreeds().first()
        Assert.assertTrue(favListBeforeUpdate.isEmpty())

        //Add data in favourite by adding isFav = true
        dogBreedsDao.updateDogBreed("bulldog", true)
        val favList: List<DogBreedEntity> = dogBreedsDao.getFavouriteDogBreeds().first()
        Assert.assertTrue(favList.isNotEmpty())
        Assert.assertEquals(true, favList[0].isFav)
    }
}