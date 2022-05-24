package com.franjam.anr.demo.rxblocking

import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * Demoing potential issues with Rx blocking APIs
 */
class BlockingRxApi {

    private val behaviorRelay: BehaviorSubject<String> = BehaviorSubject.create();

    fun getStoredId(): String {
        return behaviorRelay.blockingFirst()
    }
}