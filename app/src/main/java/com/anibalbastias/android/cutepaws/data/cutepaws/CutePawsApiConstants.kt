package com.anibalbastias.android.cutepaws.data.cutepaws

/**
 * Created by Anibal Bastias Soto on 2019-11-21.
 */

object CutePawsApiConstants {
    private const val API_VERSION = "api/"
    const val BREED_NAME = "breedName"

    const val GET_BREEDS = "${API_VERSION}breeds/list/all"
    const val GET_IMAGES_BREED = "${API_VERSION}breed/{$BREED_NAME}/images"
    const val GET_RANDOM_IMAGE_BREED = "${GET_IMAGES_BREED}/random"
}