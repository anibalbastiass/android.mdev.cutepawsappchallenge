package com.anibalbastias.android.cutepaws.presentation.module

import androidx.lifecycle.ViewModel
import com.anibalbastias.android.cutepaws.base.module.ViewModelKey
import com.anibalbastias.android.cutepaws.base.module.module.BaseViewModelModule
import com.anibalbastias.android.cutepaws.base.view.NavBaseViewModel
import com.anibalbastias.android.cutepaws.presentation.ui.breeds.viewmodel.BreedsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(NavBaseViewModel::class)
    internal abstract fun navBaseViewModel(viewModel: NavBaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BreedsViewModel::class)
    internal abstract fun cutePawsViewModel(viewModel: BreedsViewModel): ViewModel

}