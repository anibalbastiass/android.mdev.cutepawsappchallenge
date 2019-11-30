package com.anibalbastias.android.cutepaws.domain.base.executor

import io.reactivex.Scheduler

interface APIPostExecutionThread {
    val scheduler: Scheduler
}
