package com.franjam.anr.app_exit_info

import android.annotation.TargetApi
import android.app.ApplicationExitInfo
import android.os.Build
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * Gets most relevant information for ANR trace
 */
class AnrStackTraceProcessor {

    @TargetApi(Build.VERSION_CODES.R)
    fun getProcessedAnrTrace(applicationExitInfo: ApplicationExitInfo): String? {
        val processedAnrTrace = java.lang.StringBuilder()

        try {
            val anrInputStream =
                applicationExitInfo.traceInputStream // Guarding in case ANR input stream is not available
                    ?: return null
            val inputStreamReader = InputStreamReader(anrInputStream, Charset.defaultCharset())
            val bufferedReader = BufferedReader(inputStreamReader)

            var currentAnrLine = ""
            var isRelevantStackTracePart = false
            while (bufferedReader.readLine()?.also { currentAnrLine = it } != null) {

                if (currentAnrLine.startsWith(BEGIN_MAIN_TRACE_KEYWORD)) {
                    isRelevantStackTracePart = true
                }

                if (shouldIgnoreNoisyTraceLine(currentAnrLine) || !isRelevantStackTracePart) {
                    continue
                }

                if (isEndOfRelevantStackTrace(processedAnrTrace.toString(), currentAnrLine)) {
                    break
                }

                processedAnrTrace.append(currentAnrLine)
                processedAnrTrace.append(System.lineSeparator())
            }
        } catch (ioException: IOException) {
            Log.e("AnrStackTraceProcessor", "Couldn't read trace file for ANR", ioException)
        }
        return processedAnrTrace.toString()
    }

    /**
     * Ignores irrelevant trace part information. For example current ANR trace line with "|
     * sysTid=21204 nice=-10 cgrp=default sched=0/0 handle=0x75bf54b4f8" will be skipped
     */
    private fun shouldIgnoreNoisyTraceLine(currentAnrLine: String): Boolean {
        return currentAnrLine.contains(INVALID_THREAD_PART_IDENTIFIER)
    }

    /**
     * Will detect end of relevant full ANR trace if keywords contained are "- end" or contains "(not
     * attached) as this will indicate threads without any valuable information for ANR investigation
     */
    private fun isEndOfRelevantStackTrace(fullAnrTrace: String?, currentAnrLine: String): Boolean {
        return fullAnrTrace == null &&
                (currentAnrLine.contains(END_FULL_TRACE_KEYWORD) ||
                        currentAnrLine.contains(UNATTACHED_THREADS_KEYWORD))
    }

    companion object {
        /** Indicates beginning of relevant main trace */
        private const val BEGIN_MAIN_TRACE_KEYWORD = "\"main\""

        /** Keyword to detect ending of main relevant ANR trace info */
        private const val END_FULL_TRACE_KEYWORD = "- end"

        /**
         * This keyword indicates when should stop capturing ANR trace. (In this case threads with not
         * attached text are not relevant information and are located at the end of the trace)
         */
        private const val UNATTACHED_THREADS_KEYWORD = "(not attached)"

        /** Keyword used to detect irrelevant thread trace information */
        private const val INVALID_THREAD_PART_IDENTIFIER = "|"
    }
}