package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.task.TaskRetry

@OrderDsl
class OrderBasicActionsBuilder
{
    var onStart = emptyAction
    var onError = emptyErrorConsumer
    var taskRetry: TaskRetry? = null

    private fun build() = OrderBasicActions(
        onStart = onStart,
        onError = onError,
        taskRetry = taskRetry
    )

    companion object
    {
        operator fun invoke(block: OrderBasicActionsBuilder.() -> Unit) = OrderBasicActionsBuilder()
            .apply(block)
            .build()
    }
}