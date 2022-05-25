package com.franjam.anr.common_anr_pitfalls.deadlock;


import android.util.Log;

/**
 * Make sure to call first #initializeInBackground then #startProcessing
 */
public final class DeadlockDemo {
    private final static Object FIRST_LOCK_RESOURCE = "first_lock";
    private final static Object SECOND_LOCK_RESOURCE = "second_lock";

    private static final String DEADLOCK_TAG = "DEADLOCK_TAG";
    private static final long THREAD_DELAY_IN_MILLI = 10;

    public void triggerDeadlock() {
        initializeInBackground();
        startProcessing();
    }

    private void initializeInBackground() {
        Thread backgroundThread = new Thread() {
            public void run() {
                synchronized (SECOND_LOCK_RESOURCE) {
                    logThreadStatus("waiting on second lock");
                    try {
                        Thread.sleep(THREAD_DELAY_IN_MILLI);
                    } catch (InterruptedException e) {
                    }
                    synchronized (FIRST_LOCK_RESOURCE) {
                        logThreadStatus("waiting on first lock");
                    }
                }
            }
        };
        backgroundThread.setName("background_thread_for_deadlock_demo");
        backgroundThread.start();
    }

    private void startProcessing() {
        synchronized (FIRST_LOCK_RESOURCE) {
            logThreadStatus("waiting on first lock");
            try {
                Thread.sleep(THREAD_DELAY_IN_MILLI);
            } catch (InterruptedException e) {
            }

            synchronized (SECOND_LOCK_RESOURCE) {
                logThreadStatus("waiting on second lock");
            }
        }
    }

    private static void logThreadStatus(String lockInfo) {
        Log.d(DEADLOCK_TAG, "Thread [" + Thread.currentThread().getName() + "] " + lockInfo);
    }
}
