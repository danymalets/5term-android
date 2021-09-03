package com.example.calculator.expression_part

abstract class ExpressionPart(content: String, internalString: String) {
    private var content: String = content
    private var internalString: String = internalString

    fun getBeautyContent(): String = content
    fun getInternalString(): String = internalString
}