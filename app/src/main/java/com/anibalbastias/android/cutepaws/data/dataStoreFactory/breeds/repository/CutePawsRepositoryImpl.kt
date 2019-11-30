package com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.repository

import com.anibalbastias.android.cutepaws.data.cutepaws.CutePawsApiService
import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model.CutePawsData
import com.anibalbastias.android.cutepaws.domain.breeds.repository.IBreedsRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by anibalbastias on 21-11-19.
 */

@Singleton
class CutePawsRepositoryImpl @Inject constructor(private val cutePawsApiService: CutePawsApiService) :
    IBreedsRepository {

    override fun getBreedList(): Flowable<CutePawsData> =
        cutePawsApiService.getBreedList()

    override fun getDogImagesByBreed(breedName: String): Flowable<CutePawsData> =
        cutePawsApiService.getDogImagesByBreed(breedName)

    override fun getRandomDogImageByBreed(breedName: String): Flowable<CutePawsData> =
        cutePawsApiService.getRandomDogImageByBreed(breedName)
}