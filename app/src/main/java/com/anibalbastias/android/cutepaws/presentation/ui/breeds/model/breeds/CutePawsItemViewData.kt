package com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds

import android.os.Parcelable
import androidx.databinding.ObservableField
import com.anibalbastias.android.cutepaws.presentation.util.empty
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.parcel.Parcelize

/**
 * Created by anibalbastias on 2019-11-25.
 */

@Parcelize
data class CutePawsItemViewData(
    val breed: String? = null,
    val imageUrl: ObservableField<String>? = ObservableField(String.empty()),
    val subBreeds: String? = null
) : Parcelable