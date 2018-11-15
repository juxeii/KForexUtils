package com.jforex.kforexutils.engine

import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.runOrderTask

internal fun createOrder(
    orderCreationCall: KCallable<IOrder>,
    taskParams: OrderTaskParams
) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        {
            val order = orderCreationCall()
            order.kForexUtils = kForexUtils
            order
        }
    }
    .flatMap { runOrderTask(it, taskParams) }