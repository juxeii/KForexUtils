package com.jforex.kforexutils.rx

/*internal fun <T : OrderEvent> Observable<T>.subscribeForOrderEvents(params: OrderEventSubscribeParams) =
    with(params) {
        filter { it.type in eventHandlers }.subscribeBy(
            onNext = { eventHandlers.getValue(it.type)(it) },
            onComplete = { onComplete() },
            onError = { onError(it) }
        )
    }*/

/*fun Observable<OrderSubmitEvent>.subscribeForSubmit(block: SubmitEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(SubmitEventParamsBuilder(block))

fun Observable<OrderMergeEvent>.subscribeForMerge(block: MergeEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(MergeEventParamsBuilder(block))

fun Observable<OrderCloseEvent>.subscribeForClose(block: CloseEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(CloseEventParamsBuilder(block))

fun Observable<OrderAmountEvent>.subscribeForAmount(block: AmountEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(AmountEventParamsBuilder(block))

fun Observable<OrderCommentEvent>.subscribeForComment(block: CommentEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(CommentEventParamsBuilder(block))

fun Observable<OrderGTTEvent>.subscribeForGTT(block: GTTEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(GTTEventParamsBuilder(block))

fun Observable<OrderLabelEvent>.subscribeForLabel(block: LabelEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(LabelEventParamsBuilder(block))

fun Observable<OrderOpenPriceEvent>.subscribeForOpenPrice(block: OpenPriceEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(OpenPriceEventParamsBuilder(block))

fun Observable<OrderTPEvent>.subscribeForTP(block: TPEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(TPEventParamsBuilder(block))

fun Observable<OrderSLEvent>.subscribeForSL(block: SLEventParamsBuilder.() -> Unit) =
    subscribeForOrderEvents(SLEventParamsBuilder(block))*/
