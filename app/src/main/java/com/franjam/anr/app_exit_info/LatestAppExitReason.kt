package com.franjam.anr.app_exit_info

import android.annotation.TargetApi
import android.app.ActivityManager
import android.app.ApplicationExitInfo
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.StringBuilder

/**
 * Retrieves the latest reason for app termination
 *
 * NOTE: Only provides useful information on OS 11 and up
 */
class LatestAppExitReason(applicationContext: Context) {

    private val activityManager =
        applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    private val appPackageName: String = applicationContext.packageName

    /**
     * Retrieves latest exit reason upon cold launch
     *
     * NOTE: This method should not be called on UI thread
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun getLatestExitReason(): ExitReasonData? {
        checkForMainThread();
        val lastKnownExitReasonIfPresent = getLastKnownExitReasonIfPresent()
        lastKnownExitReasonIfPresent?.let { applicationExitInfo ->
            val exitReasonType = mapToReadableExitReason(applicationExitInfo)
            val description = applicationExitInfo.description
            var anrData: AnrData? = null // Will updated in next commit

            if (exitReasonType == ExitReasonType.REASON_ANR) {
                val anrStackTraceProcessor = AnrStackTraceProcessor()
                val anrStackTrace =
                    anrStackTraceProcessor.getProcessedAnrTrace(applicationExitInfo);
                anrStackTrace?.let { processedAnrTrace ->
                    anrData = AnrData(processedAnrTrace)
                }
            }
            return ExitReasonData(exitReasonType, description, anrData)
        }
        return null
    }

    /**
     * Get last exit reason information in a readable manner.
     *
     * NOTE: This method should not be called on UI thread
     */
    fun getReadableExitReasonText(): String {
        var reasonDetails = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val latestAppExitReason = getLatestExitReason()
            latestAppExitReason?.let {
                val readableReason = StringBuilder()
                readableReason.append("Last reason for app termination: " + it.latestExitReason + DEFAULT_LINE_BREAK)
                readableReason.append("Description: " + it.description + DEFAULT_LINE_BREAK)
                it.anrData?.let { anrData ->
                    readableReason.append("Full Stacktrace: " + anrData.processedStackTrace)
                }
                reasonDetails = readableReason.toString()
            }
        } else {
            reasonDetails =
                "Latest exit reason unknown. Application Exit Info only available on OS 11 and up"
        }
        return reasonDetails
    }

    /**
     * Mapping to a readable exit reason as ApplicationExitInfo reasons are just int constants
     */
    @RequiresApi(Build.VERSION_CODES.R)
    private fun mapToReadableExitReason(applicationExitInfo: ApplicationExitInfo): ExitReasonType {
        return when (applicationExitInfo.reason) {
            ApplicationExitInfo.REASON_ANR -> ExitReasonType.REASON_ANR
            ApplicationExitInfo.REASON_CRASH -> ExitReasonType.REASON_CRASH
            ApplicationExitInfo.REASON_CRASH_NATIVE -> ExitReasonType.REASON_CRASH_NATIVE
            ApplicationExitInfo.REASON_DEPENDENCY_DIED -> ExitReasonType.REASON_DEPENDENCY_DIED
            ApplicationExitInfo.REASON_EXCESSIVE_RESOURCE_USAGE ->
                ExitReasonType.REASON_EXCESSIVE_RESOURCE_USAGE
            ApplicationExitInfo.REASON_EXIT_SELF -> ExitReasonType.REASON_EXIT_SELF
            ApplicationExitInfo.REASON_INITIALIZATION_FAILURE ->
                ExitReasonType.REASON_INITIALIZATION_FAILURE
            ApplicationExitInfo.REASON_LOW_MEMORY -> ExitReasonType.REASON_LOW_MEMORY
            ApplicationExitInfo.REASON_OTHER -> ExitReasonType.REASON_OTHER
            ApplicationExitInfo.REASON_PERMISSION_CHANGE ->
                ExitReasonType.REASON_PERMISSION_CHANGE
            ApplicationExitInfo.REASON_SIGNALED -> ExitReasonType.REASON_SIGNALED
            ApplicationExitInfo.REASON_UNKNOWN -> ExitReasonType.REASON_UNKNOWN
            ApplicationExitInfo.REASON_USER_REQUESTED -> ExitReasonType.REASON_USER_REQUESTED
            ApplicationExitInfo.REASON_USER_STOPPED -> ExitReasonType.REASON_USER_STOPPED
            else -> ExitReasonType.NOT_DEFINED
        }
    }

    @TargetApi(Build.VERSION_CODES.R)
    private fun getLastKnownExitReasonIfPresent(): ApplicationExitInfo? {
        val applicationExitInfoList: List<ApplicationExitInfo> =
            activityManager.getHistoricalProcessExitReasons(
                appPackageName, PID_FOR_EXIT_REASONS, MAX_REASONS_NUMBER
            )
        return applicationExitInfoList.firstOrNull { applicationExitInfo: ApplicationExitInfo ->
            appPackageName == applicationExitInfo.processName
        }
    }

    private fun checkForMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.e(LOGCAT_TAG, "Called on main thread")
        }
    }

    companion object {

        const val LOGCAT_TAG = "LatestAppExitReason"

        const val DEFAULT_LINE_BREAK = "\n\n"

        /*
        * From android dev docs: A process ID that used to belong to this package but died later; a
        * value of 0 means to  ignore this parameter and return all matching records. Value is 0 or
        * greater
        */
        const val PID_FOR_EXIT_REASONS = 0

        /*
        * From android dev docs: The maximum number of results to be returned; a value of 0 means to
        * ignore
        * this parameter and all matching records Value is 0 or greater
        */
        const val MAX_REASONS_NUMBER = 0
    }
}