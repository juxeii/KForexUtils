package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.order.task.OrderEventHandlerParams

interface IParamsBuilder<T>
{
    fun build(block: T.() -> Unit): OrderEventHandlerParams
}