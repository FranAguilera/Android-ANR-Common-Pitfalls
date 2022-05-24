package com.franjam.anr.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.franjam.anr.demo.basic_components.BroadcastReceiverDemo
import com.franjam.anr.demo.deadlock.GenerateDeadlock
import com.franjam.anr.demo.expensive_operation.ReportDetails
import com.franjam.anr.demo.rxblocking.BlockingRxApi

class ANRDemoActivity : AppCompatActivity() {

    private lateinit var longOperationOnUiThreadButton: Button
    private lateinit var blockingApiCallButton: Button
    private lateinit var broadcastReceiverButton: Button
    private lateinit var deadlockButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        longOperationOnUiThreadButton = findViewById(R.id.long_ui_operation)
        blockingApiCallButton = findViewById(R.id.blocking_api)
        broadcastReceiverButton = findViewById(R.id.android_components)
        deadlockButton = findViewById(R.id.deadlock)

        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        val buttonClickListener = ButtonClickListener()
        longOperationOnUiThreadButton.setOnClickListener(buttonClickListener)
        blockingApiCallButton.setOnClickListener(buttonClickListener)
        broadcastReceiverButton.setOnClickListener(buttonClickListener)
        deadlockButton.setOnClickListener(buttonClickListener)
    }

    private class ButtonClickListener : View.OnClickListener {
        override fun onClick(currentView: View?) {
            currentView?.let {
                when (it.id) {
                    R.id.long_ui_operation -> ReportDetails().getInformationFromFile()
                    R.id.blocking_api -> BlockingRxApi().getStoredId()
                    R.id.android_components -> BroadcastReceiverDemo().sendBroadcast()
                    R.id.deadlock -> GenerateDeadlock().getUserIdInBackgroundThread()
                    else -> Log.d(LOGCAT_TAG, "Unwanted listener for button view")
                }
            }
        }
    }

    companion object {
        const val LOGCAT_TAG: String = "ANR_DEMO"
    }
}