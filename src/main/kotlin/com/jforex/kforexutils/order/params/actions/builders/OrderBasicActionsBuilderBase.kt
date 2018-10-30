package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.order.params.actions.OrderTaskActions

abstract class OrderBasicActionsBuilderBase
{
    var basicActions = OrderTaskActions()

    fun basicActions(block: OrderTaskActionsBuilder.() -> Unit)
    {
        basicActions = OrderTaskActionsBuilder(block)
    }
}