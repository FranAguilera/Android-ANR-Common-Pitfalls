package com.franjam.anr.common_anr_pitfalls

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class CoroutineBlockingDemo {
    fun callBlocking() = runBlocking {
        longRunningCode()
    }

    suspend fun longRunningCode() {
        delay(10000L)
    }
}