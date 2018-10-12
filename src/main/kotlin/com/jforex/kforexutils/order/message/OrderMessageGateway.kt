package com.jforex.kforexutils.order.message

import com.jforex.kforexutils.message.MessageFilter
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.order.OrderStore
import io.reactivex.Observable

class OrderMessageGateway(
    private val messageGateway: MessageGateway,
    private val orderStore: OrderStore
) {
    val observable: Observable<OrderMessage> = messageGateway
        .observable
        .filter(MessageFilter.ORDER::isMatch)
        .map { Pair(orderStore.getForJFOrder(it.order), it) }
        .filter { it.first.isPresent }
        .map { OrderMessage(it.first.get(), it.second) }
}