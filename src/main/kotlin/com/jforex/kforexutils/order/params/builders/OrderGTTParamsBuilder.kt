package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderGTTParams
import com.jforex.kforexutils.order.params.actions.OrderGTTActions
import com.jforex.kforexutils.order.params.actions.builders.OrderGTTActionsBuilder

@OrderDsl
class OrderGTTParamsBuilder(private val gtt: Long)
{
    private var actions = OrderGTTActions()

    fun actions(block: OrderGTTActionsBuilder.() -> Unit) {
        actions = OrderGTTActionsBuilder(block)
    }

    fun build() = OrderGTTParams(
        gtt = gtt,
        actions = actions
    )

    companion object {
        operator fun invoke(gtt: Long, block: OrderGTTParamsBuilder.() -> Unit) =
            OrderGTTParamsBuilder(gtt)
                .apply(block)
                .build()
    }
}