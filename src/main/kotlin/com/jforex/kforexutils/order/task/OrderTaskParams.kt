package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.emptyOrderCallParams

data class OrderTaskParams(
    val eventParams: OrderTaskEventParams,
    val callParams: OrderCallParams = emptyOrderCallParams
)