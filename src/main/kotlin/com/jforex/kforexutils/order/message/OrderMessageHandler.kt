package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.message.MessageToOrderMessageType
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumerType
import com.jforex.kforexutils.order.event.consumer.OrderEventHandler
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class OrderMessageHandler(
    private val order: IOrder,
    private val orderMessageGateway: OrderMessageGateway
) {
    private var orderEvents = orderMessageGateway
        .observable
        .filter { it.order == order }
        .map { messageToOrderEvent(it) }
    private var changeConsumers = ConcurrentLinkedQueue<OrderEventHandler>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    private fun messageToOrderEvent(orderMessage: IMessage): OrderEvent {
        val messageType = MessageToOrderMessageType.convert(orderMessage)
        return OrderEvent(orderMessage, messageType)
    }

    private fun subscribeNextConsumer() {
        val currentConsumer = changeConsumers.poll()
        logger.debug("Removed current change consumer with type ${currentConsumer.type} from queue")
        if (!changeConsumers.isEmpty()) {
            logger.debug("Subscribing next change consumer with type ${currentConsumer.type} from queue")
            changeConsumers
                .peek()
                .subscribe(orderEvents) { -> subscribeNextConsumer() }
        }
    }

    fun registerConsumer(consumer: OrderEventHandler) {
        when (consumer.type) {
            OrderEventConsumerType.SUBMIT,
            OrderEventConsumerType.CLOSE,
            OrderEventConsumerType.MERGE -> {
                logger.debug("Consumer with type ${consumer.type} subscribes to order events.")
                consumer.subscribe(orderEvents)
            }
            else -> {
                logger.debug("Change consumer with type ${consumer.type} gets added to consumer queue")
                if (changeConsumers.isEmpty())
                    consumer.subscribe(orderEvents)
                changeConsumers.add(consumer)
            }
        }
    }
}