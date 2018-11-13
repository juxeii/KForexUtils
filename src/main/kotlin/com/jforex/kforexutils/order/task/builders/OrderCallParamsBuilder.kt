package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyTaskRetryHandler
import com.jforex.kforexutils.order.task.OrderCallActions
import com.jforex.kforexutils.order.task.OrderCallParams
import com.jforex.kforexutils.order.task.TaskRetry

@OrderDsl
class OrderCallParamsBuilder
{
    private var callActions = OrderCallActions()
    var retryHandler: TaskRetry = emptyTaskRetryHandler

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderCallParams(
        callActions = callActions,
        retryHandler = retryHandler
    )

    companion object
    {
        operator fun invoke(block: OrderCallParamsBuilder.() -> Unit) =
            OrderCallParamsBuilder()
                .apply(block)
                .build()
    }
}