package com.anibalbastias.android.cutepaws.data.cutepaws

import com.anibalbastias.android.cutepaws.data.cutepaws.CutePawsApiConstants.BREED_NAME
import com.anibalbastias.android.cutepaws.data.cutepaws.CutePawsApiConstants.GET_BREEDS
import com.anibalbastias.android.cutepaws.data.cutepaws.CutePawsApiConstants.GET_IMAGES_BREED
import com.anibalbastias.android.cutepaws.data.cutepaws.CutePawsApiConstants.GET_RANDOM_IMAGE_BREED
import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model.CutePawsData
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by anibalbastias on 3/03/19.
 */

interface CutePawsApiService {

    //region Breeds
    @GET(GET_BREEDS)
    fun getBreedList(): Flowable<CutePawsData>

    @GET(GET_IMAGES_BREED)
    fun getDogImagesByBreed(@Path(BREED_NAME) breedName: String): Flowable<CutePawsData>

    @GET(GET_RANDOM_IMAGE_BREED)
    fun getRandomDogImageByBreed(@Path(BREED_NAME) breedName: String): Flowable<CutePawsData>
    //endregion

}