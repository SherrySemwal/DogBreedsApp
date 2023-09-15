package com.sherry.dogapp.di

import android.content.Context
import androidx.room.Room
import com.sherry.dogapp.db.DogBreedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val NAME = "dog_breed_db"

    @Singleton
    @Provides
    fun provideDogBreedDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        DogBreedDatabase::class.java,
        NAME
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideDogBreedDao(db: DogBreedDatabase) = db.dogBreedsDao()
}