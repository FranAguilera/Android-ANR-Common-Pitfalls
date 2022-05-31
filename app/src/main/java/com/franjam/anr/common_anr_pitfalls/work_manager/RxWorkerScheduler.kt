package com.franjam.anr.common_anr_pitfalls.work_manager

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest

class RxWorkerScheduler() {
    fun scheduleWork(context: Context) {
        val rxDownloadWorker: WorkRequest =
            OneTimeWorkRequestBuilder<RxDownloadWorker>()
                .build()
        WorkManager
            .getInstance(context)
            .enqueue(rxDownloadWorker)
    }
}