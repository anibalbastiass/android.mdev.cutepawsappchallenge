package com.anibalbastias.android.cutepaws.presentation.module

import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.repository.CutePawsRepositoryImpl
import com.anibalbastias.android.cutepaws.domain.breeds.repository.IBreedsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CutePawsRepositoryModule {

    @Binds
    abstract fun bindProductsDataRepository(repository: CutePawsRepositoryImpl): IBreedsRepository

}