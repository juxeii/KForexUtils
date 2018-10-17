package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.order.params.actions.OrderBasicActions

abstract class OrderActionsBuilderBase
{
    var basicActions = OrderBasicActions()

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit)
    {
        basicActions = OrderBasicActionsBuilder(block)

    }
}