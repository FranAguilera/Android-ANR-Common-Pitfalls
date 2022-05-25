package com.franjam.anr.app_exit_info

data class ExitReasonData(
    val latestExitReason: ExitReasonType,
    val description: String?,
    val anrData:AnrData?
)