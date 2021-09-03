package com.example.calculator.expression_part

abstract class ExpressionPart(
    private var content: String,
    private var internalString: String) {

    fun getBeautyContent(): String = content
    fun getInternalString(): String = internalString
}