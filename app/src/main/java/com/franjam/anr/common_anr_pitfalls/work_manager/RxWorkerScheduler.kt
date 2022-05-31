package com.franjam.anr.common_anr_pitfalls.work_manager

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class RxWorkerScheduler() {
    fun scheduleWork(context: Context) {
        val rxDownloadWorker: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<RxDownloadWorker>()
                .build()
        WorkManager
            .getInstance(context)
            .enqueueUniqueWork("RxWorkerScheduler", ExistingWorkPolicy.REPLACE, rxDownloadWorker)
    }
}