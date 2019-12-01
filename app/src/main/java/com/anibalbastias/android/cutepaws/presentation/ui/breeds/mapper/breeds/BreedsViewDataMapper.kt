package com.anibalbastias.android.cutepaws.presentation.ui.breeds.mapper.breeds

import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model.CutePawsData
import com.anibalbastias.android.cutepaws.domain.base.Mapper
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsItemViewData

import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsViewData
import com.anibalbastias.android.cutepaws.presentation.util.empty
import com.google.gson.internal.LinkedTreeMap
import javax.inject.Inject


/**
 * Created by anibalbastias on 2019-11-25.
 */

open class BreedsViewDataMapper @Inject constructor() : Mapper<CutePawsViewData?, CutePawsData?> {

    override fun executeMapping(type: CutePawsData?): CutePawsViewData? {
        return type?.let {
            CutePawsViewData(
                code = it.code,
                success = it.success,
                status = it.status,
                disclaimer = it.message as? String,
                list = it.message as? ArrayList<String>,
                breedList = getBreedList(it.message as? LinkedTreeMap<String, ArrayList<String>>)
            )
        }
    }

    private fun getBreedList(map: LinkedTreeMap<String, ArrayList<String>>?): ArrayList<CutePawsItemViewData> {
        val breedList = arrayListOf<CutePawsItemViewData>()
        map?.forEach { item ->
            var subBreedList = String.empty()

            item.value.forEach { subitem ->
                subBreedList += "• ${subitem.capitalize()}\n"
            }

            breedList.add(
                CutePawsItemViewData(
                    breed = item.key.capitalize(),
                    subBreeds = subBreedList
                )
            )
        }
        return breedList
    }
}