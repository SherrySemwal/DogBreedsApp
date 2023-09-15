package com.sherry.dogapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sherry.dogapp.model.DogBreedDataModel

@Entity
data class DogBreedEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val breedName: String,
    val subTypeCount: Int,
    val breedImageUrl: String?,
    var isFav: Boolean = false
)

fun DogBreedDataModel.toDogBreedEntity() = DogBreedEntity(
    breedName = breedName,
    subTypeCount = subTypeCount,
    breedImageUrl = breedImageUrl,
    isFav = isFav
)