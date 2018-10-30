package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.order.task.OrderTaskActions
import com.jforex.kforexutils.order.task.OrderTaskActionsBuilder

abstract class OrderTaskActionsBuilderBase {
    var taskActions = OrderTaskActions()

    fun taskActions(block: OrderTaskActionsBuilder.() -> Unit)
    {
        taskActions = OrderTaskActionsBuilder(block)
    }
}