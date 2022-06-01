package com.franjam.anr.common_anr_pitfalls.coroutines

import com.franjam.anr.logging.Logger.printCurrentThreadInfo
import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep

class CoroutineBlockingDemo {
    var myId = ""
    fun callBlocking(context: Context): String = runBlocking {
        launch(Dispatchers.IO) {
            myId = getIdFromNetwork()
        }
        myId
    }
    
    suspend fun getIdFromNetwork(): String {
        printCurrentThreadInfo("CoroutineBlockingDemo.getIdFromNetwork")
        sleep(20000)
        return "myId"
    }
}