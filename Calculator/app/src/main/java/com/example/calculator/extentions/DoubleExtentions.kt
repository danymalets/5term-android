package com.example.calculator.extentions

fun Double.toBeautyString(): String {
    val long  = this.toLong()
    return if (this == long.toDouble())
        String.format("%d", this.toLong());
    else
        String.format("%s", this);
}