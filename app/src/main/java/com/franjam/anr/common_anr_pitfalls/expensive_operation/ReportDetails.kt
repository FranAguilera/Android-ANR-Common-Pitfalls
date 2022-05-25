package com.franjam.anr.common_anr_pitfalls.expensive_operation

class ReportDetails {

    fun getInformationFromFile():String{
        Thread.sleep(20000)
        // TBF with real expensive method
        return "Info retrieved"
    }
}