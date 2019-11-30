package com.anibalbastias.android.cutepaws.presentation.ui.breeds.mapper.breeds

import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model.CutePawsData
import com.anibalbastias.android.cutepaws.domain.base.Mapper

import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsViewData
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
                hashMap = it.message as? HashMap<String, ArrayList<String>>
            )
        }
    }
}