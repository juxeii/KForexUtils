package com.jforex.kforexutils.order.extension

import arrow.core.value
import com.dukascopy.api.IOrder
import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setSL(
    slPrice: Double,
    offerSide: OfferSide = OfferSide.BID,
    trailingStep: Double = TradingSettings.noTrailingStep,
    block: OrderCallHandlerBuilder.() -> Unit = {}
) = runOrderTask(
    orderCallable = changeToCallableCall(this) {
        setStopLossPrice(
            slPrice,
            offerSide,
            trailingStep
        )
    },
    taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), ChangeEventData(OrderEventType.CHANGED_SL))
).run(kForexUtils).value()

fun IOrder.removeSL(block: OrderCallHandlerBuilder.() -> Unit = {}) = setSL(
    TradingSettings.noSLPrice, OfferSide.BID,
    TradingSettings.noTrailingStep,
    block
)