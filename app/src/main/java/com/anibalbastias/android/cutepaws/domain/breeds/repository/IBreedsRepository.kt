package com.anibalbastias.android.cutepaws.domain.breeds.repository

import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model.CutePawsData
import io.reactivex.Flowable

/**
 * Created by anibalbastias on 3/19/18.
 */
interface IBreedsRepository {
    fun getBreedList(): Flowable<CutePawsData>
    fun getDogImagesByBreed(breedName: String): Flowable<CutePawsData>
    fun getRandomDogImageByBreed(breedName: String): Flowable<CutePawsData>
}