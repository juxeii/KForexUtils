package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskData

data class OrderMergeActions(
    val taskData: OrderTaskData = OrderTaskData(),
    val onMerge: OrderEventConsumer = emptyOrderEventConsumer,
    val onMergeClose: OrderEventConsumer = emptyOrderEventConsumer
)