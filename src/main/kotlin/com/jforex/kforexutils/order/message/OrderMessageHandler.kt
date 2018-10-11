package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.message.MessageToOrderMessageType
import com.jforex.kforexutils.order.OrderRequestType
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumerType
import com.jforex.kforexutils.order.extension.isCanceled
import com.jforex.kforexutils.order.extension.isClosed
import org.apache.logging.log4j.LogManager
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class OrderMessageHandler(val order: IOrder)
{
    private val emptyConsumer: Optional<OrderEventConsumer> = Optional.empty()
    private var submitConsumer = emptyConsumer
    private var closeConsumer = emptyConsumer
    private var mergeConsumer = emptyConsumer
    private var changeConsumers = ConcurrentLinkedQueue<OrderEventConsumer>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    init
    {
        OrderMessageGateway
            .observable()
            .doOnNext { logger.debug("Without filter received ${it.type}") }
            .filter { it.order == order }
            .doOnNext { logger.debug("Received order message ${it.type}") }
            .takeUntil { order.isClosed() || order.isCanceled() }
            .doOnComplete { logger.debug("Order is closed now") }
            .subscribe(this::onMessage)
    }

    private fun onMessage(orderMessage: IMessage)
    {
        val messageType = MessageToOrderMessageType.convert(orderMessage)
        val orderEvent = OrderEvent(orderMessage, messageType)
        val requestType = OrderMessageTypeToOrderRequestType.convert(messageType)

        selectConsumer(requestType).ifPresent {
            logger.debug("Sending order event $orderEvent to consumer with type ${it.type}")
            it.onOrderEvent(orderEvent)
        }
    }

    private fun selectConsumer(requestType: OrderRequestType): Optional<OrderEventConsumer>
    {
        val consumer = when (requestType)
        {
            OrderRequestType.SUBMIT -> submitConsumer
            OrderRequestType.CLOSE -> closeConsumer
            OrderRequestType.MERGE -> mergeConsumer
            OrderRequestType.CHANGE ->
            {
                logger.debug("Calling change handler now! IS queue empty ${changeConsumers.isEmpty()}")
                if (!changeConsumers.isEmpty())
                {
                    if (changeConsumers.element().isCompleted())
                    {
                        changeConsumers.poll()
                        val nextConsumer = changeConsumers.peek()
                        nextConsumer?.subscribe()
                        Optional.ofNullable(nextConsumer)
                    } else Optional.ofNullable(changeConsumers.peek())
                } else emptyConsumer
            }
            else -> emptyConsumer
        }
        return consumer
    }

    fun registerConsumer(consumer: OrderEventConsumer)
    {
        when (consumer.type)
        {
            OrderEventConsumerType.SUBMIT ->
            {
                logger.debug("Registering submit consumer")
                consumer.subscribe()
                submitConsumer = Optional.of(consumer)
            }
            OrderEventConsumerType.CLOSE ->
            {
                logger.debug("Registering close consumer")
                consumer.subscribe()
                closeConsumer = Optional.of(consumer)
            }
            OrderEventConsumerType.CHANGE_SL,
            OrderEventConsumerType.CHANGE_TP,
            OrderEventConsumerType.CHANGE_LABEL,
            OrderEventConsumerType.CHANGE_AMOUNT,
            OrderEventConsumerType.CHANGE_PRICE,
            OrderEventConsumerType.CHANGE_GTT,
            OrderEventConsumerType.CHANGE_TYPE ->
            {
                logger.debug("Registering ${consumer.type} consumer. Adding it to queue")
                if (changeConsumers.isEmpty()) consumer.subscribe()
                changeConsumers.add(consumer)
            }
        }
    }
}