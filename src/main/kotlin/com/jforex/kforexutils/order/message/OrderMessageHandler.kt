package com.jforex.kforexutils.order.message

import com.jforex.kforexutils.message.MessageToOrderMessageType
import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.OrderRequestType
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumerType
import org.apache.logging.log4j.LogManager
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class OrderMessageHandler(val order: Order, private val orderMessageGateway: OrderMessageGateway) {
    private val emptyConsumer: Optional<OrderEventConsumer> = Optional.empty()
    private var submitConsumer = emptyConsumer
    private var closeConsumer = emptyConsumer
    private var mergeConsumer = emptyConsumer
    private var changeConsumers = ConcurrentLinkedQueue<OrderEventConsumer>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    init {
        orderMessageGateway
            .observable
            .filter { it.order == order }
            .doOnNext { logger.debug("Received order message ${it.message.type}") }
            .takeUntil { isOrderActive(it.order) }
            .doOnComplete { logger.debug("Order ${order.jfOrder.label} no longer active") }
            .subscribe(this::onMessage)
    }

    private fun isOrderActive(order: Order) = order.isClosed() || order.isCanceled()

    private fun onMessage(orderMessage: OrderMessage) {
        val messageType = MessageToOrderMessageType.convert(orderMessage.message)
        val orderEvent = OrderEvent(orderMessage, messageType)
        val requestType = OrderMessageTypeToOrderRequestType.convert(messageType)

        selectConsumer(requestType).ifPresent {
            logger.debug("Sending order event $orderEvent to consumer with type ${it.type}")
            it.onOrderEvent(orderEvent)
        }
    }

    private fun selectConsumer(requestType: OrderRequestType): Optional<OrderEventConsumer> {
        val consumer = when (requestType) {
            OrderRequestType.SUBMIT -> submitConsumer
            OrderRequestType.CLOSE -> closeConsumer
            OrderRequestType.MERGE -> mergeConsumer
            OrderRequestType.CHANGE -> {
                logger.debug("Calling change handler now! IS queue empty ${changeConsumers.isEmpty()}")
                if (!changeConsumers.isEmpty()) {
                    if (changeConsumers.element().isCompleted()) {
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

    fun registerConsumer(consumer: OrderEventConsumer) {
        when (consumer.type) {
            OrderEventConsumerType.SUBMIT -> {
                logger.debug("Registering submit consumer")
                consumer.subscribe()
                submitConsumer = Optional.of(consumer)
            }
            OrderEventConsumerType.CLOSE -> {
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
            OrderEventConsumerType.CHANGE_TYPE -> {
                logger.debug("Registering ${consumer.type} consumer. Adding it to queue")
                if (changeConsumers.isEmpty()) consumer.subscribe()
                changeConsumers.add(consumer)
            }
        }
    }
}