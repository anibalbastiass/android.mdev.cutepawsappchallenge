package com.anibalbastias.android.cutepaws.domain.breeds.usecase

import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model.CutePawsData
import com.anibalbastias.android.cutepaws.domain.base.interactor.FlowableUseCase
import com.anibalbastias.android.cutepaws.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.cutepaws.domain.base.executor.APIThreadExecutor
import com.anibalbastias.android.cutepaws.domain.breeds.repository.IBreedsRepository
import io.reactivex.Flowable
import javax.inject.Inject

open class GetRandomImageBreedUseCase @Inject constructor(
    private val breedsRepository: IBreedsRepository,
    threadExecutor: APIThreadExecutor,
    postExecutionThread: APIPostExecutionThread
) : FlowableUseCase<CutePawsData, String?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Flowable<CutePawsData> =
        breedsRepository.getRandomDogImageByBreed(params!!)

}