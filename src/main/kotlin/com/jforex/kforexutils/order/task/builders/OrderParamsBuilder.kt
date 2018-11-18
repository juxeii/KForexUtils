package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.EventHandlers
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallHandlers
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderParamsBuilder<T>(private val params: IParamsBuilder<T>)
{
    private var callHandlers = emptyCallHandlers
    private var eventHandlerParams = params.build { }

    fun callHandlers(block: OrderCallHandlerBuilder.() -> Unit)
    {
        callHandlers = OrderCallHandlerBuilder(block)
    }

    fun eventHandlers(block: T.() -> Unit)
    {
        eventHandlerParams = params.build(block)
    }

    fun build() = OrderTaskParams(
        callHandlers = callHandlers,
        eventHandlerParams = eventHandlerParams
    )

    companion object
    {
        operator fun <T> invoke(
            params: IParamsBuilder<T>,
            block: OrderParamsBuilder<T>.() -> Unit
        ) = OrderParamsBuilder(params)
            .apply(block)
            .build()
    }
}

internal fun filterEventHandlers(handlers: EventHandlers) = handlers.filterValues { it != emptyOrderEventConsumer }