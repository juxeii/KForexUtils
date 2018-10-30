package com.jforex.kforexutils.rx

import io.reactivex.Observable
import io.reactivex.Observer
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class ObservableQueue<T> {
    private data class QueueItem<T>(
        val observable: Observable<T>,
        val observer: Observer<T>
    ) {
        fun subscribe() = observable.subscribe(observer)
    }

    private var queuedItems = ConcurrentLinkedQueue<QueueItem<T>>()
    private val logger = LogManager.getLogger(this.javaClass.name)

    @Synchronized
    fun add(
        observable: Observable<T>,
        observer: Observer<T>
    ) {
        logger.debug("Adding observer to queue.")
        val queueItem = QueueItem(observable.doOnComplete(::subscribeNext), observer)
        if (queuedItems.isEmpty()) queueItem.subscribe()
        queuedItems.add(queueItem)
    }

    @Synchronized
    private fun subscribeNext() {
        if (queuedItems.isEmpty()) return

        logger.debug("Subscribing next observer")
        queuedItems.poll()
        queuedItems.peek()?.subscribe()
    }
}