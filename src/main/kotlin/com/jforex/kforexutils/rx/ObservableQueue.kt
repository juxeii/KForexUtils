package com.jforex.kforexutils.rx

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class ObservableQueue<T>(observable: Observable<T>) {
    private var queuedObservers = ConcurrentLinkedQueue<PublishRelay<T>>()
    private val logger = LogManager.getLogger(this.javaClass.name)

    init {
        observable.subscribe { if (queuedObservers.isEmpty()) onQueueNotEmpty(it) }
    }

    private fun onQueueNotEmpty(item: T) {
        val relay = queuedObservers.element()
        if (relay.hasObservers()) relay.accept(item)
        else onNoObserverPresent(item)
    }

    private fun onNoObserverPresent(item: T) {
        logger.debug("Current relay has no more observers, so item gets pushed to next observer")
        queuedObservers.remove()
        queuedObservers.peek()?.accept(item)
    }

    fun enqueue(): Observable<T> {
        val relay: PublishRelay<T> = PublishRelay.create()
        queuedObservers.add(relay)
        logger.debug("Added relay to observer queue.")
        return relay
    }
}