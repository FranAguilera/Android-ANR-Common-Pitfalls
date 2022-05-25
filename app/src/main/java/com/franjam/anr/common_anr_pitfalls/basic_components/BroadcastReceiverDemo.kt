package com.franjam.anr.common_anr_pitfalls.basic_components

import android.content.Context
import android.content.Intent

import com.franjam.anr.common_anr_pitfalls.basic_components.SampleBroadcastWithAnr.Companion.ACTION_NAME


class BroadcastReceiverDemo {
    fun sendBroadcast(context: Context) {
        val intent = Intent(context, SampleBroadcastWithAnr::class.java)
        intent.action = ACTION_NAME
        context.sendBroadcast(intent)
    }
}