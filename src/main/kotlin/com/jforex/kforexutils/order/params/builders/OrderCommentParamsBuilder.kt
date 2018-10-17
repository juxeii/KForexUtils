package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderCommentParams
import com.jforex.kforexutils.order.params.actions.OrderCommentActions
import com.jforex.kforexutils.order.params.actions.builders.OrderCommentActionsBuilder

@OrderDsl
class OrderCommentParamsBuilder(private val comment: String)
{
    private var actions = OrderCommentActions()

    fun actions(block: OrderCommentActionsBuilder.() -> Unit)
    {
        actions = OrderCommentActionsBuilder(block)
    }

    fun build() = OrderCommentParams(
        comment = comment,
        actions = actions
    )

    companion object
    {
        operator fun invoke(comment: String, block: OrderCommentParamsBuilder.() -> Unit) =
            OrderCommentParamsBuilder(comment)
                .apply(block)
                .build()
    }
}