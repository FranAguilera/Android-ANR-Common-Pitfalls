package com.franjam.anr.logging

import android.util.Log

object Logger {

    private const val LOGCAT_TAG = "ANR_COMMON_PITFALLS"

    fun printCurrentThreadInfo(message: String) {
        val currentThreadName = Thread.currentThread().name
        Log.d(LOGCAT_TAG, "$message called in [ $currentThreadName]")
    }
}