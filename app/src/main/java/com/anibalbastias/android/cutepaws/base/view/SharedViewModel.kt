package com.anibalbastias.android.cutepaws.base.view

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsItemViewData
import javax.inject.Inject

/**
 * Created by anibalbastias on 2019-09-08.
 */

class SharedViewModel @Inject constructor(val state: SavedStateHandle) : BaseViewModel() {

    // Keep the key as a constant
    companion object {
        private const val RESULT_ITEM_KEY = "resultItemKey"
    }

    private val savedStateHandle = state

    private val breedItemLiveData: MutableLiveData<CutePawsItemViewData> = savedStateHandle.getLiveData(RESULT_ITEM_KEY)

    var breedItemViewData: CutePawsItemViewData
        get() = breedItemLiveData.value ?: CutePawsItemViewData()
        set(value) {
            breedItemLiveData.value = value
        }
}