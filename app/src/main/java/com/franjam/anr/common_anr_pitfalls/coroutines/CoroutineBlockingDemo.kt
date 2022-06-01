package com.franjam.anr.common_anr_pitfalls.coroutines

import com.franjam.anr.logging.Logger.printCurrentThreadInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class CoroutineBlockingDemo {
    fun callBlocking() = runBlocking {
        longRunningCode()
    }

    suspend fun longRunningCode() {
        printCurrentThreadInfo("CoroutineBlockingDemo.longRunningCode")
        delay(10000L)
    }
}