package com.franjam.anr.demo.expensive_operation

class ReportDetails {

    fun getInformationFromFile():String{
        Thread.sleep(20000)
        // TBF with real expensive method
        return "Info retrieved"
    }
}