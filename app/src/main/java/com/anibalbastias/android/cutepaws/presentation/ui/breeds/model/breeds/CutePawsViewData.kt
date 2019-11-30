package com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by anibalbastias on 2019-11-25.
 */

@Parcelize
data class CutePawsViewData(
    val success: String? = null,
    val code: Int? = null,
    val status: String? = null,

    // Possible message objects
    var disclaimer: String? = null,
    var list: ArrayList<String>? = null,
    var hashMap: HashMap<String, ArrayList<String>>? = null
) : Parcelable