package com.sherry.dogapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sherry.dogapp.db.entity.DogBreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogBreedsDao {

    @Query("SELECT * FROM dogbreedentity")
    fun getDogBreedList(): Flow<List<DogBreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDogBreeds(dogBreeds: List<DogBreedEntity>)

    @Query("UPDATE dogbreedentity SET isFav = :isFav WHERE breedName = :name")
    suspend fun updateDogBreed(name: String, isFav: Boolean)

    @Query("SELECT * FROM dogbreedentity WHERE isFav = 1")
    fun getFavouriteDogBreeds(): Flow<List<DogBreedEntity>>
}