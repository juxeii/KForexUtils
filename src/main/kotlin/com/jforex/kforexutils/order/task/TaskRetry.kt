package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent

interface TaskRetry
{
    fun onRejectEvent(rejectEvent: OrderEvent, retryCall: KRunnable)
}