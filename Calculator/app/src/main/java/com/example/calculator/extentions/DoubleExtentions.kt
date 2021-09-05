package com.example.calculator.extentions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.text.*

fun Double.toBeautyString(): String {
    val long  = this.toLong()
    val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    df.maximumFractionDigits = 340
    return if (this == long.toDouble())
        String.format("%d", this.toLong())
    else
        String.format("%s", df.format(this))

}