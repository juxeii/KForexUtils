package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyTaskRetryHandler
import com.jforex.kforexutils.order.params.actions.builders.OrderCallActionsBuilder
import com.jforex.kforexutils.order.task.OrderCallParams
import com.jforex.kforexutils.order.task.OrderTaskData
import com.jforex.kforexutils.order.task.TaskRetry

@OrderDsl
class OrderTaskUserParamsBuilder {
    private var taskData = OrderTaskData()
    private var retryHandler: TaskRetry = emptyTaskRetryHandler

    fun taskData(block: OrderCallActionsBuilder.() -> Unit) {
        taskData = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderCallParams(
        taskData = taskData,
        retryHandler = retryHandler
    )

    companion object {
        operator fun invoke(block: OrderTaskUserParamsBuilder.() -> Unit) =
            OrderTaskUserParamsBuilder()
                .apply(block)
                .build()
    }
}