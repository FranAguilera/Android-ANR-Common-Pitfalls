package com.franjam.anr.common_anr_pitfalls.work_manager

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

class RxDownloadWorker(
    context: Context,
    params: WorkerParameters
) : RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        Log.d("RxDownloadWorker", "createWork() at " + currentThreadName())
        return Single.just(downloadContent("http://sampleurl.com"))
    }

    private fun downloadContent(targetUrl: String): Result {
        Log.d(LOGCAT_TAG, "downloadContent() at " + currentThreadName())
        simulateLongDownload();
        return Result.success()
    }

    private fun simulateLongDownload() {
        Thread.sleep(20000)
    }

    private fun currentThreadName(): String {
        return "[" + Thread.currentThread().name + "]"
    }

    companion object {
        private const val LOGCAT_TAG = "RxDownloadWorker"
    }
}