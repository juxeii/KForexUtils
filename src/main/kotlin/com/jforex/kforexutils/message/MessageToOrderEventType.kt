package com.jforex.kforexutils.message

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.extension.isFilled
import io.reactivex.rxkotlin.toObservable

class MessageToOrderEventType
{
    private val changeTypeByReason = mapOf(
        IMessage.Reason.ORDER_FULLY_FILLED to OrderEventType.FULLY_FILLED,
        IMessage.Reason.ORDER_CHANGED_AMOUNT to OrderEventType.CHANGED_AMOUNT,
        IMessage.Reason.ORDER_CHANGED_GTT to OrderEventType.CHANGED_GTT,
        IMessage.Reason.ORDER_CHANGED_LABEL to OrderEventType.CHANGED_LABEL,
        IMessage.Reason.ORDER_CHANGED_PRICE to OrderEventType.CHANGED_PRICE,
        IMessage.Reason.ORDER_CHANGED_SL to OrderEventType.CHANGED_SL,
        IMessage.Reason.ORDER_CHANGED_TP to OrderEventType.CHANGED_TP,
        IMessage.Reason.ORDER_CHANGED_TYPE to OrderEventType.CHANGED_TYPE
    )

    private val closeTypeByReason = mapOf(
        IMessage.Reason.ORDER_CLOSED_BY_SL to OrderEventType.CLOSED_BY_SL,
        IMessage.Reason.ORDER_CLOSED_BY_TP to OrderEventType.CLOSED_BY_TP,
        IMessage.Reason.ORDER_CLOSED_BY_MERGE to OrderEventType.CLOSED_BY_MERGE
    )

    fun get(orderMessage: IMessage) = OrderEvent(orderMessage.order, getType(orderMessage))

    private fun getType(orderMessage: IMessage): OrderEventType =
        when (orderMessage.type) {
            IMessage.Type.ORDER_SUBMIT_REJECTED -> OrderEventType.SUBMIT_REJECTED
            IMessage.Type.ORDER_SUBMIT_OK -> OrderEventType.SUBMIT_OK
            IMessage.Type.ORDER_FILL_REJECTED -> OrderEventType.FILL_REJECTED
            IMessage.Type.ORDER_CLOSE_REJECTED -> OrderEventType.CLOSE_REJECTED
            IMessage.Type.ORDER_CLOSE_OK -> convertForClosedOrder(orderMessage)
            IMessage.Type.ORDER_FILL_OK -> convertForOrderFill(orderMessage.order)
            IMessage.Type.ORDERS_MERGE_OK -> convertForOrderMerge(orderMessage)
            IMessage.Type.ORDERS_MERGE_REJECTED -> OrderEventType.MERGE_REJECTED
            IMessage.Type.ORDER_CHANGED_OK -> convertForOrderChange(orderMessage)
            IMessage.Type.ORDER_CHANGED_REJECTED -> OrderEventType.CHANGE_REJECTED
            else -> OrderEventType.NOTIFICATION
        }

    private fun convertForClosedOrder(orderMessage: IMessage) =
        if (orderMessage.order.isFilled) OrderEventType.PARTIAL_CLOSE_OK
        else convertIfReasonMatch(orderMessage.reasons, closeTypeByReason)
            .defaultIfEmpty(OrderEventType.CLOSE_OK)
            .blockingGet()

    private fun convertForOrderFill(order: IOrder) =
        if (order.amount < order.requestedAmount) OrderEventType.PARTIALLY_FILLED
        else OrderEventType.FULLY_FILLED

    private fun convertForOrderMerge(orderMessage: IMessage) =
        if (orderMessage.order.isFilled) OrderEventType.MERGE_OK
        else OrderEventType.MERGE_CLOSE_OK

    private fun convertForOrderChange(orderMessage: IMessage) =
        convertIfReasonMatch(orderMessage.reasons, changeTypeByReason)
            .defaultIfEmpty(OrderEventType.PARTIALLY_FILLED)
            .blockingGet()

    private fun convertIfReasonMatch(
        reasons: Set<IMessage.Reason>,
        typeByReason: Map<IMessage.Reason, OrderEventType>
    ) = typeByReason
        .entries
        .toObservable()
        .skipWhile { !reasons.contains(it.key) }
        .map { it.value }
        .firstElement()
}