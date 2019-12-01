package com.anibalbastias.android.cutepaws.presentation.component

import com.anibalbastias.android.cutepaws.presentation.module.ApplicationModule
import com.anibalbastias.android.cutepaws.presentation.module.CutePawsAPIModule
import com.anibalbastias.android.cutepaws.presentation.module.ViewModelModule
import com.anibalbastias.android.cutepaws.presentation.MainActivity
import com.anibalbastias.android.cutepaws.base.module.component.BaseApplicationComponent
import com.anibalbastias.android.cutepaws.presentation.module.CutePawsRepositoryModule
import com.anibalbastias.android.cutepaws.presentation.ui.breedDetail.BreedsDetailFragment
import com.anibalbastias.android.cutepaws.presentation.ui.entry.EntryFragment
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.BreedsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        CutePawsRepositoryModule::class,
        ViewModelModule::class,
        CutePawsAPIModule::class]
)

interface ApplicationComponent : BaseApplicationComponent, FragmentInjector {
    fun inject(mainActivity: MainActivity)
}

interface FragmentInjector {
    fun inject(entryFragment: EntryFragment)
    fun inject(breedsListFragment: BreedsListFragment)
    fun inject(breedsDetailFragment: BreedsDetailFragment)
}