package com.franjam.anr.common_anr_pitfalls.deadlock

import android.util.Log
import com.franjam.anr.common_anr_pitfalls.deadlock.DeadlockDemo

/**
 * Make sure to call first #initializeInBackground then #startProcessing
 */
class DeadlockDemo {
    fun triggerDeadlock() {
        initializeInBackground()
        startProcessing()
    }

    private fun initializeInBackground() {
        val backgroundThread: Thread = object : Thread() {
            override fun run() {
                synchronized(SECOND_LOCK_RESOURCE) {
                    logThreadStatus("waiting on second lock")
                    try {
                        sleep(THREAD_DELAY_IN_MILLI)
                    } catch (e: InterruptedException) {
                    }
                    synchronized(FIRST_LOCK_RESOURCE) { logThreadStatus("waiting on first lock") }
                }
            }
        }
        backgroundThread.name = "background_thread_for_deadlock_demo"
        backgroundThread.start()
    }

    private fun startProcessing() {
        synchronized(FIRST_LOCK_RESOURCE) {
            logThreadStatus("waiting on first lock")
            try {
                Thread.sleep(THREAD_DELAY_IN_MILLI)
            } catch (e: InterruptedException) {
            }
            synchronized(SECOND_LOCK_RESOURCE) { logThreadStatus("waiting on second lock") }
        }
    }

    companion object {
        private val FIRST_LOCK_RESOURCE: Any = "first_lock"
        private val SECOND_LOCK_RESOURCE: Any = "second_lock"
        private const val DEADLOCK_TAG = "DEADLOCK_TAG"
        private const val THREAD_DELAY_IN_MILLI: Long = 10
        private fun logThreadStatus(lockInfo: String) {
            Log.d(DEADLOCK_TAG, "Thread [" + Thread.currentThread().name + "] " + lockInfo)
        }
    }
}