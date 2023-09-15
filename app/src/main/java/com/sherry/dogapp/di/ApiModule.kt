package com.sherry.dogapp.di


import com.sherry.dogapp.api.ApiInterface
import com.sherry.dogapp.api.local.ImpLocalDataDataSource
import com.sherry.dogapp.api.local.LocalDataSource
import com.sherry.dogapp.api.remote.ImpRemoteDataSource
import com.sherry.dogapp.api.remote.RemoteDataSource
import com.sherry.dogapp.db.dao.DogBreedsDao
import com.sherry.dogapp.repository.DogBreedRepository
import com.sherry.dogapp.repository.ImpDogBreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: ApiInterface): RemoteDataSource = ImpRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: DogBreedsDao): LocalDataSource = ImpLocalDataDataSource(dao)

    @Singleton
    @Provides
    fun provideDogBreedRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): DogBreedRepository = ImpDogBreedRepository(remoteDataSource, localDataSource, Dispatchers.IO)
}