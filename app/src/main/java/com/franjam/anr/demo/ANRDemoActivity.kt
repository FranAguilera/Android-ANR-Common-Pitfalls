package com.franjam.anr.demo

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.franjam.anr.app_exit_info.LatestAppExitReason
import com.franjam.anr.demo.basic_components.BroadcastReceiverDemo
import com.franjam.anr.demo.deadlock.DeadlockDemo
import com.franjam.anr.demo.expensive_operation.ReportDetails
import com.franjam.anr.demo.rxblocking.BlockingRxApi


class ANRDemoActivity : AppCompatActivity() {

    private lateinit var longOperationOnUiThreadButton: Button
    private lateinit var blockingApiCallButton: Button
    private lateinit var broadcastReceiverButton: Button
    private lateinit var deadlockButton: Button
    private lateinit var latestExitReasonText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        longOperationOnUiThreadButton = findViewById(R.id.long_ui_operation)
        blockingApiCallButton = findViewById(R.id.blocking_api)
        broadcastReceiverButton = findViewById(R.id.android_components)
        deadlockButton = findViewById(R.id.deadlock)
        latestExitReasonText = findViewById(R.id.latest_exit_reason_text)
        latestExitReasonText.movementMethod = ScrollingMovementMethod()

        setupButtonClickListeners()
        displayLastExitReason()
    }


    private fun setupButtonClickListeners() {
        val buttonClickListener = ButtonClickListener()
        longOperationOnUiThreadButton.setOnClickListener(buttonClickListener)
        blockingApiCallButton.setOnClickListener(buttonClickListener)
        broadcastReceiverButton.setOnClickListener(buttonClickListener)
        deadlockButton.setOnClickListener(buttonClickListener)
    }

    private fun displayLastExitReason() {
        val latestAppExitReason = LatestAppExitReason(applicationContext)
        latestExitReasonText.text = latestAppExitReason.getReadableExitReasonText()
    }

    private class ButtonClickListener : View.OnClickListener {
        override fun onClick(currentView: View?) {
            currentView?.let {
                when (it.id) {
                    R.id.long_ui_operation -> ReportDetails().getInformationFromFile()
                    R.id.blocking_api -> BlockingRxApi().getStoredId()
                    R.id.android_components -> BroadcastReceiverDemo().sendBroadcast()
                    R.id.deadlock -> DeadlockDemo().triggerDeadlock()
                    else -> Log.d(LOGCAT_TAG, "Unwanted listener for button view")
                }
            }
        }
    }

    companion object {
        const val LOGCAT_TAG: String = "ANR_DEMO"
    }
}