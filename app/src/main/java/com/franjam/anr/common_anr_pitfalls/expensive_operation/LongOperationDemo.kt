package com.franjam.anr.common_anr_pitfalls.expensive_operation

class LongOperationDemo {

    fun longRunningMethod() {
        for (i in 1..TOTAL_SORTS_REQUIRED) {
            awfulSort()
        }
    }

    private fun awfulSort() {
        val arrayToBeSorted = getOriginalSequence()
        val totalSize = arrayToBeSorted.size
        for (i in 0 until totalSize) {
            for (j in 0 until totalSize - 1) {
                if (arrayToBeSorted[j] > arrayToBeSorted[j + 1]) {
                    val temp: Int = arrayToBeSorted[j]
                    arrayToBeSorted[j] = arrayToBeSorted[j + 1]
                    arrayToBeSorted[j + 1] = temp
                }
            }
        }
    }

    private fun getOriginalSequence(): Array<Int> {
        return arrayOf(
            5248,
            2157,
            7610,
            8255,
            317,
            2039,
            1270,
            4019,
            8550,
            237,
            5245,
            7823,
            9491,
            7994,
            7749,
            4014,
            5169,
            3679,
            9726,
            3304,
            9933,
            77,
            2325,
            9343,
            4710,
            3586,
            3611,
            1092,
            2194,
            3171,
            1653,
            6101,
            3803,
            4431,
            6308,
            5298,
            7118,
            5080,
            4229,
            6418,
            4337,
            211,
            4386,
            955,
            9397,
            3182,
            7376,
            6213,
            3583,
            9268,
            9211,
            3685,
            8399,
            3149,
            218,
            8478,
            8096,
            6675,
            9465,
            2866,
            4333,
            6341,
            7727,
            4410,
            3765,
            9421,
            3192,
            541,
            240,
            1112,
            1555,
            8472,
            7363,
            4624
        )
    }

    companion object {
        const val TOTAL_SORTS_REQUIRED = 1000000
    }
}