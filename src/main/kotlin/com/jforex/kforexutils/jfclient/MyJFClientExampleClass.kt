package com.jforex.kforexutils.jfclient

import com.jforex.kforexutils.misc.KForexUtils

class MyJFClientExampleClass : JFClientBridge
{
    override fun onStart(utils: KForexUtils)
    {
        //From here on you can work with KForexUtils
        //Let's start with open an order

        /*val engine = utils.engine
        engine.submit(
            label = "MyLabel",
            amount = 0.001,
            instrument = Instrument.EURUSD,
            orderCommand = IEngine.OrderCommand.BUY
        ) {
            stopLossPrice = 1.0101
            takeProfitPrice = 2.2989
            actions {
                onSubmit = { println("Order ${it.order.label} has been submitted") }
                onFullFill = { println("Order ${it.order.label} has been fully filled.") }
            }
        }*/
    }

    override fun onStop()
    {
        //You can do cleanup here
    }
}