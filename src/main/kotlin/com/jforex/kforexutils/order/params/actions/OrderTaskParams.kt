package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.order.event.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderCallParams

data class OrderTaskParams(
    val eventParams: OrderTaskEventParams,
    val callParams: OrderCallParams = emptyOrderCallParams
)