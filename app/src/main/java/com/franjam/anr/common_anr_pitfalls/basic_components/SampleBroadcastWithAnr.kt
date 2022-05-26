package com.franjam.anr.common_anr_pitfalls.basic_components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.franjam.anr.common_anr_pitfalls.expensive_operation.LongOperationDemo


class SampleBroadcastWithAnr : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {
            if (it == ACTION_NAME) {
                displayToaster(context, ACTION_NAME)
            }
            val longOperationDemo = LongOperationDemo()
            longOperationDemo.longRunningMethod()
        }
    }

    private fun displayToaster(context: Context?, message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val ACTION_NAME = "ANR_BROADCAST_RECEIVER_ACTION"
    }
}