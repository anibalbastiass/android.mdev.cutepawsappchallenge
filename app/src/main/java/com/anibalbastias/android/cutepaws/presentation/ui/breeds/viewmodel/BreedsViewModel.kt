package com.anibalbastias.android.cutepaws.presentation.ui.breeds.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.subscriber.BaseSubscriber
import com.anibalbastias.android.cutepaws.base.view.BaseViewModel
import com.anibalbastias.android.cutepaws.base.view.Resource
import com.anibalbastias.android.cutepaws.base.view.ResourceState
import com.anibalbastias.android.cutepaws.domain.breeds.usecase.GetBreedsUseCase
import com.anibalbastias.android.cutepaws.domain.breeds.usecase.GetRandomImageBreedUseCase
import com.anibalbastias.android.cutepaws.presentation.context
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.mapper.breeds.BreedsViewDataMapper
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsItemViewData
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.model.breeds.CutePawsViewData
import javax.inject.Inject

/**
 * Created by anibalbastias on 2019-11-25.
 */

class BreedsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val getRandomImageBreedUseCase: GetRandomImageBreedUseCase,
    private val breedsViewDataMapper: BreedsViewDataMapper
) : BaseViewModel() {

    // region Observables
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isError: ObservableBoolean = ObservableBoolean(false)
    var cutePawsList: ObservableField<ArrayList<CutePawsItemViewData>> = ObservableField(arrayListOf())
    var itemPosition: ObservableInt = ObservableInt(0)
    // endregion

    var cutePawsItemLayout: Int? = R.layout.view_cell_breed_item

    override fun onCleared() {
        getBreedsUseCase.dispose()
        getRandomImageBreedUseCase.dispose()
        super.onCleared()
    }

    //region Live Data
    private val getBreedsLiveData: MutableLiveData<Resource<CutePawsViewData?>> = MutableLiveData()
    fun getBreedsLiveData() = getBreedsLiveData

    private val getBreedsImageLiveData: MutableLiveData<Resource<CutePawsViewData?>> = MutableLiveData()
    fun getBreedsImageLiveData() = getBreedsImageLiveData
    //endregion

    fun getSearchSongsResultsData() {
        isLoading.set(true)
        getBreedsLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        return getBreedsUseCase.execute(
            BaseSubscriber(
                context?.applicationContext, this, breedsViewDataMapper,
                getBreedsLiveData, isLoading, isError
            )
        )
    }

    fun getRandomImageBreed(breed: String) {
        getBreedsImageLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        return getRandomImageBreedUseCase.execute(
            BaseSubscriber(
                context?.applicationContext, this, breedsViewDataMapper,
                getBreedsImageLiveData, isLoading, isError
            ), breed
        )
    }
}