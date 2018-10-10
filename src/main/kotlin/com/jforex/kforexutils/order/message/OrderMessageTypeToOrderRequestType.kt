package com.jforex.kforexutils.order.message

import com.jforex.kforexutils.order.OrderRequestType
import com.jforex.kforexutils.order.event.OrderEventType

object OrderMessageTypeToOrderRequestType
{
    private val requestTypeByMessageType = mapOf(
        OrderEventType.NOTIFICATION to OrderRequestType.NONE,
        OrderEventType.SUBMIT_OK to OrderRequestType.SUBMIT,
        OrderEventType.SUBMIT_REJECTED to OrderRequestType.SUBMIT,
        OrderEventType.FULLY_FILLED to OrderRequestType.SUBMIT,
        OrderEventType.PARTIALLY_FILLED to OrderRequestType.SUBMIT,
        OrderEventType.FILL_REJECTED to OrderRequestType.SUBMIT,
        OrderEventType.MERGE_OK to OrderRequestType.MERGE,
        OrderEventType.MERGE_CLOSE_OK to OrderRequestType.MERGE,
        OrderEventType.MERGE_REJECTED to OrderRequestType.MERGE,
        OrderEventType.CLOSE_OK to OrderRequestType.CLOSE,
        OrderEventType.PARTIAL_CLOSE_OK to OrderRequestType.CLOSE,
        OrderEventType.CLOSED_BY_MERGE to OrderRequestType.CLOSE,
        OrderEventType.CLOSED_BY_SL to OrderRequestType.CLOSE,
        OrderEventType.CLOSED_BY_TP to OrderRequestType.CLOSE,
        OrderEventType.CLOSE_REJECTED to OrderRequestType.CLOSE,
        OrderEventType.CHANGED_SL to OrderRequestType.CHANGE,
        OrderEventType.CHANGED_TP to OrderRequestType.CHANGE,
        OrderEventType.CHANGED_LABEL to OrderRequestType.CHANGE,
        OrderEventType.CHANGED_AMOUNT to OrderRequestType.CHANGE,
        OrderEventType.CHANGED_PRICE to OrderRequestType.CHANGE,
        OrderEventType.CHANGED_GTT to OrderRequestType.CHANGE,
        OrderEventType.CHANGED_TYPE to OrderRequestType.CHANGE,
        OrderEventType.CHANGE_REJECTED to OrderRequestType.CHANGE
    )

    fun convert(orderMessageType: OrderEventType) = requestTypeByMessageType.getValue(orderMessageType)
}