package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.engine.kForexUtils
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams
import com.jforex.kforexutils.settings.PlatformSettings
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import org.aeonbits.owner.ConfigFactory

class KForexUtils(val context: IContext) {
    private val completionTriggers: PublishRelay<Unit> = PublishRelay.create()
    private val changeEventHandlers: PublishRelay<ExecutionData> = PublishRelay.create()

    val engine = context.engine
    private val messagePublisher: PublishRelay<IMessage> = PublishRelay.create()
    val messageGateway = MessageGateway(messagePublisher)
    private val orderEventConverter = MessageToOrderEventType()
    val orderMessageGateway = OrderEventGateway(
        messageGateway.messages,
        orderEventConverter
    )
    val orderEvents = orderMessageGateway.observable
    val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)

    init {
        engine.kForexUtils = this

        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToEvents(it) { completeTask() } })

        completeTask()
    }

    private fun completeTask() = completionTriggers.accept(Unit)

    fun registerHandler(
        order: IOrder,
        params: OrderTaskExecutionParams
    ) {
        val executionData = ExecutionData(order, params)
        if (params.eventParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
            changeEventHandlers.accept(executionData)
        else subscribeToEvents(executionData) {}
    }

    private fun subscribeToEvents(
        data: ExecutionData,
        completionCall: KRunnable
    ) {
        subscribeToOrderEvents(data.params) { completionCall }.run(orderEvents.filter { it.order == data.order })
    }
}

data class ExecutionData(
    val order: IOrder,
    val params: OrderTaskExecutionParams
)