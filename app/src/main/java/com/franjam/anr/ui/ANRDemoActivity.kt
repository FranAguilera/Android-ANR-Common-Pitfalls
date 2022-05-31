package com.franjam.anr.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.franjam.anr.app_exit_info.LatestAppExitReason
import com.franjam.anr.common_anr_pitfalls.CoroutineBlockingDemo
import com.franjam.anr.common_anr_pitfalls.basic_components.BroadcastReceiverDemo
import com.franjam.anr.common_anr_pitfalls.deadlock.DeadlockDemo
import com.franjam.anr.demo.R
import com.franjam.anr.common_anr_pitfalls.expensive_operation.LongOperationDemo
import com.franjam.anr.common_anr_pitfalls.rxblocking.BlockingRxApiDemo
import com.franjam.anr.common_anr_pitfalls.work_manager.RxWorkerScheduler
import kotlinx.coroutines.CoroutineScope


class ANRDemoActivity : AppCompatActivity() {

    private lateinit var longOperationOnUiThreadButton: Button
    private lateinit var blockingRxApiCallButton: Button
    private lateinit var blockingCoroutineButton: Button
    private lateinit var broadcastReceiverButton: Button
    private lateinit var rxWorkerButton: Button
    private lateinit var deadlockButton: Button
    private lateinit var latestExitReasonText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        longOperationOnUiThreadButton = findViewById(R.id.long_ui_operation)
        blockingRxApiCallButton = findViewById(R.id.blocking_rx_api)
        blockingCoroutineButton = findViewById(R.id.blocking_coroutine)
        broadcastReceiverButton = findViewById(R.id.android_components)
        deadlockButton = findViewById(R.id.deadlock)
        rxWorkerButton = findViewById(R.id.rx_worker)
        latestExitReasonText = findViewById(R.id.latest_exit_reason_text)
        latestExitReasonText.movementMethod = ScrollingMovementMethod()

        setupButtonClickListeners()
        displayLastExitReason()
    }

    private fun setupButtonClickListeners() {
        val buttonClickListener = ButtonClickListener()
        longOperationOnUiThreadButton.setOnClickListener(buttonClickListener)
        blockingRxApiCallButton.setOnClickListener(buttonClickListener)
        blockingCoroutineButton.setOnClickListener(buttonClickListener)
        broadcastReceiverButton.setOnClickListener(buttonClickListener)
        rxWorkerButton.setOnClickListener(buttonClickListener)
        deadlockButton.setOnClickListener(buttonClickListener)
    }

    private fun displayLastExitReason() {
        val latestAppExitReason = LatestAppExitReason(applicationContext)
        latestExitReasonText.text = latestAppExitReason.getReadableExitReasonText()
    }

    private inner class ButtonClickListener : View.OnClickListener {
        override fun onClick(currentView: View?) {
            currentView?.let {
                when (it.id) {
                    R.id.long_ui_operation -> LongOperationDemo().longRunningMethod()
                    R.id.blocking_rx_api -> BlockingRxApiDemo().getOrderId()
                    R.id.blocking_coroutine -> CoroutineBlockingDemo().callBlocking()
                    R.id.android_components -> BroadcastReceiverDemo().sendBroadcast(it.context)
                    R.id.rx_worker -> RxWorkerScheduler().scheduleWork(applicationContext)
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