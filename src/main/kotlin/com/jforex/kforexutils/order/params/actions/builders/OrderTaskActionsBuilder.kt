package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import com.jforex.kforexutils.misc.emptyOrderConsumer
import com.jforex.kforexutils.order.params.actions.OrderTaskActions
import com.jforex.kforexutils.order.task.TaskRetry

@OrderDsl
class OrderTaskActionsBuilder
{
    var onStart = emptyAction
    var onSuccess = emptyOrderConsumer
    var onError = emptyErrorConsumer
    var taskRetry: TaskRetry? = null

    private fun build() = OrderTaskActions(
        onStart = onStart,
        onSuccess = onSuccess,
        onError = onError,
        taskRetry = taskRetry
    )

    companion object
    {
        operator fun invoke(block: OrderTaskActionsBuilder.() -> Unit) = OrderTaskActionsBuilder()
            .apply(block)
            .build()
    }
}