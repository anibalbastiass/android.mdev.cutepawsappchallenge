package com.anibalbastias.android.cutepaws.base.module.module

import com.anibalbastias.android.cutepaws.presentation.CutePawsApplication
import com.anibalbastias.android.cutepaws.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.cutepaws.domain.base.executor.APIThreadExecutor
import com.anibalbastias.android.cutepaws.base.module.executor.JobExecutor
import com.anibalbastias.android.cutepaws.base.module.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class BaseApplicationModule(private val application: CutePawsApplication) {

    @Provides
    @Singleton
    fun provideApp(): CutePawsApplication = application

    @Provides
    @Singleton
    fun provideAPIThreadExecutor(jobExecutor: JobExecutor): APIThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun provideAPIPostExecutionThread(uiThread: UIThread): APIPostExecutionThread = uiThread
}