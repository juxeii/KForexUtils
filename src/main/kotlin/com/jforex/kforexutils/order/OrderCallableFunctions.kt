package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KRunnable

internal fun changeToCallableCall(
    order: IOrder,
    changeCall: KRunnable
) = {
    changeCall()
    order
}

internal fun changeToCallWithOrderInit(orderCreationCall: KCallable<IOrder>) = {
    val order = orderCreationCall()
    order
}