package com.jforex.kforexutils.order.event

import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.event.handler.OrderEventObserver
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class OrderEventHandler(private val orderEvents: Observable<OrderEvent>)
{
    private var changeHandlers = ConcurrentLinkedQueue<OrderEventObserver>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    private fun subscribeNextHandler()
    {
        if (changeHandlers.isEmpty()) return

        val currentHandler = changeHandlers.poll()
        logger.debug("Removed current change handler with type ${currentHandler.type} from queue")
        if (!changeHandlers.isEmpty())
        {
            val nextHandler = changeHandlers.peek()
            logger.debug("Subscribing next change handler with type ${nextHandler.type} from queue")
            nextHandler.subscribe(orderEvents) { subscribeNextHandler() }
        }
    }

    fun registerHandler(handlerData: OrderEventHandlerData)
    {
        val observer = OrderEventObserver(handlerData)
        when (observer.type)
        {
            OrderEventHandlerType.SUBMIT,
            OrderEventHandlerType.CLOSE,
            OrderEventHandlerType.MERGE ->
            {
                logger.debug("Consumer with type ${observer.type} subscribes to order events.")
                observer.subscribe(orderEvents)
            }
            else ->
            {
                logger.debug("Change handler with type ${observer.type} gets added to handler queue")
                if (changeHandlers.isEmpty())
                    observer.subscribe(orderEvents) { subscribeNextHandler() }
                changeHandlers.add(observer)
            }
        }
    }
}