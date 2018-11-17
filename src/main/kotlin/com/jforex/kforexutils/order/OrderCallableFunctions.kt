package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.extension.kForexUtils

internal fun changeToCallableCall(
    order: IOrder,
    changeCall: KRunnable
) = {
    changeCall()
    order
}

internal fun changeToCallWithOrderInit(
    kForexUtils: KForexUtils,
    orderCreationCall: KCallable<IOrder>
) = {
    val order = orderCreationCall()
    order.kForexUtils = kForexUtils
    order
}