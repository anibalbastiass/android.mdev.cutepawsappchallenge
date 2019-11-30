package com.anibalbastias.android.cutepaws.base.module.component

import com.anibalbastias.android.cutepaws.presentation.CutePawsApplication
import com.anibalbastias.android.cutepaws.base.module.executor.JobExecutor
import com.anibalbastias.android.cutepaws.base.module.executor.UIThread


interface BaseApplicationComponent {

    fun inject(application: CutePawsApplication)
    fun threadExecutor(): JobExecutor
    fun postExecutionThread(): UIThread
}