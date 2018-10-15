package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.message.MessageToOrderMessageType
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.consumer.OrderEventHandler
import com.jforex.kforexutils.order.event.consumer.OrderEventHandlerType
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class OrderMessageHandler(
    private val order: IOrder,
    private val orderMessageGateway: OrderMessageGateway
)
{
    private var orderEvents = orderMessageGateway
        .observable
        .filter { it.order == order }
        .map { messageToOrderEvent(it) }
    private var changeHandlers = ConcurrentLinkedQueue<OrderEventHandler>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    private fun messageToOrderEvent(orderMessage: IMessage): OrderEvent
    {
        val messageType = MessageToOrderMessageType.convert(orderMessage)
        return OrderEvent(orderMessage, messageType)
    }

    private fun subscribeNextConsumer()
    {
        if (changeHandlers.isEmpty()) return

        val currentConsumer = changeHandlers.poll()
        logger.debug("Removed current change consumer with type ${currentConsumer.type} from queue")
        if (!changeHandlers.isEmpty())
        {
            val nextConsumer = changeHandlers.peek()
            logger.debug("Subscribing next change consumer with type ${nextConsumer.type} from queue")
            nextConsumer.subscribe(orderEvents) { -> subscribeNextConsumer() }
        }
    }

    fun registerConsumer(consumerData: OrderEventConsumerData)
    {
        val handler = OrderEventHandler(consumerData)
        when (handler.type)
        {
            OrderEventHandlerType.SUBMIT,
            OrderEventHandlerType.CLOSE,
            OrderEventHandlerType.MERGE ->
            {
                logger.debug("Consumer with type ${handler.type} subscribes to order events.")
                handler.subscribe(orderEvents)
            }
            else ->
            {
                logger.debug("Change handler with type ${handler.type} gets added to handler queue")
                if (changeHandlers.isEmpty())
                    handler.subscribe(orderEvents) { -> subscribeNextConsumer() }
                changeHandlers.add(handler)
            }
        }
    }
}