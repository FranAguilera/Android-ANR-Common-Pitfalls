package com.franjam.anr.common_anr_pitfalls.expensive_operation

import android.util.Log

class LongOperationDemo {

    fun longRunningMethod() {
        val arrayToBeSorted = getOriginalSequence()
        Log.d("LongOperationDemo", "Original array is " + arrayToBeSorted.contentToString())
        val outputArray = awfulSort(arrayToBeSorted)
        Log.d("LongOperationDemo", "Output array is " + outputArray.contentToString())
    }

    private fun awfulSort(arrayToBeSorted: Array<Int>): Array<Int> {
        val totalSize = arrayToBeSorted.size
        for (i in 0 until totalSize) {
            for (j in 0 until totalSize - 1) {
                if (arrayToBeSorted[j] > arrayToBeSorted[j + 1]) {
                    val currentValue: Int = arrayToBeSorted[j]
                    arrayToBeSorted[j] = arrayToBeSorted[j + 1]
                    arrayToBeSorted[j + 1] = currentValue
                }
            }
        }
        return arrayToBeSorted
    }

    private fun getOriginalSequence(): Array<Int> {
        return Array(TOTAL_SEQUENCE_SIZE) { it * (1..100).random() }
    }

    companion object {
        const val TOTAL_SEQUENCE_SIZE = 1000000
    }
}