package com.sherry.dogapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sherry.dogapp.db.dao.DogBreedsDao
import com.sherry.dogapp.db.entity.DogBreedEntity

@Database(version = 1, entities = [DogBreedEntity::class], exportSchema = false)
abstract class DogBreedDatabase : RoomDatabase() {
    abstract fun dogBreedsDao(): DogBreedsDao
}