package com.jforex.kforexutils.order.message

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.event.handler.data.OrderEventConsumerData
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class OrderMessageHandler(
    private val order: IOrder,
    orderEventGateway: OrderEventGateway
) {
    private var orderEvents = orderEventGateway
        .observable
        .filter { it.order == order }
    private var changeHandlers = ConcurrentLinkedQueue<OrderEventHandler>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    private fun subscribeNextConsumer() {
        if (changeHandlers.isEmpty()) return

        val currentHandler = changeHandlers.poll()
        logger.debug("Removed current change handler with type ${currentHandler.type} from queue")
        if (!changeHandlers.isEmpty()) {
            val nextHandler = changeHandlers.peek()
            logger.debug("Subscribing next change handler with type ${nextHandler.type} from queue")
            nextHandler.subscribe(orderEvents) { subscribeNextConsumer() }
        }
    }

    fun registerConsumer(consumerData: OrderEventConsumerData) {
        val handler = OrderEventHandler(consumerData)
        when (handler.type) {
            OrderEventHandlerType.SUBMIT,
            OrderEventHandlerType.CLOSE,
            OrderEventHandlerType.MERGE -> {
                logger.debug("Consumer with type ${handler.type} subscribes to order events.")
                handler.subscribe(orderEvents)
            }
            else -> {
                logger.debug("Change handler with type ${handler.type} gets added to handler queue")
                if (changeHandlers.isEmpty())
                    handler.subscribe(orderEvents) { subscribeNextConsumer() }
                changeHandlers.add(handler)
            }
        }
    }
}