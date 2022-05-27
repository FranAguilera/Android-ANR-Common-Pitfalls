package com.franjam.anr.common_anr_pitfalls.rxblocking

import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * Demoing potential issues with Rx blocking APIs
 */
class BlockingRxApiDemo {

    private val orderIdSubject: BehaviorSubject<String> = BehaviorSubject.create();

    fun getOrderId(): String {
        return orderIdSubject.blockingFirst()
    }
}