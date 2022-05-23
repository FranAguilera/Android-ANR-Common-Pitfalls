package com.franjam.anr.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class ANRDemoActivity : AppCompatActivity() {

    private lateinit var longOperationOnUiThreadButton: Button
    private lateinit var blockingApiCallButton: Button
    private lateinit var broadcastReceiverDemo: Button
    private lateinit var deadlockDemo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        longOperationOnUiThreadButton = findViewById(R.id.long_ui_operation)
        blockingApiCallButton = findViewById(R.id.blocking_api)
        broadcastReceiverDemo = findViewById(R.id.android_components)
        deadlockDemo = findViewById(R.id.deadlock)

        val buttonClickListener = ButtonClickListener()
        longOperationOnUiThreadButton.setOnClickListener(buttonClickListener)
        blockingApiCallButton.setOnClickListener(buttonClickListener)
        broadcastReceiverDemo.setOnClickListener(buttonClickListener)
        deadlockDemo.setOnClickListener(buttonClickListener)
    }


    private class ButtonClickListener : View.OnClickListener {
        override fun onClick(currentView: View?) {
            currentView?.let {
                when (it.id) {
                    R.id.long_ui_operation -> Log.d("ANR_DEMO", "long operation on UI thread button clicked")
                    R.id.blocking_api -> Log.d("ANR_DEMO", "blockingApiCallButton button clicked")
                    R.id.android_components -> Log.d("ANR_DEMO", "broadcastReceiverDemo button clicked")
                    R.id.deadlock -> Log.d("ANR_DEMO", "deadlockDemo button clicked")
                    else -> Log.d("", "Failed to load button click listener")
                }

            }
        }
    }
}