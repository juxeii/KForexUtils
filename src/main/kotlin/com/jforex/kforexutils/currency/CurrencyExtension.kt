package com.jforex.kforexutils.currency

import com.dukascopy.api.ICurrency

fun ICurrency.isIsoCurrency() = javaCurrency != null