package com.example.calculator.expression_part

abstract class ExpressionPart(
    private var content: String,
    private var internalString: String,
    private var leftMultiplication: Boolean,
    private var rightMultiplication: Boolean) {

    fun getBeautyContent(): String = content
    fun getInternalString(): String = internalString
    fun isLeftMultiplicationRequired(): Boolean = leftMultiplication
    fun isRightMultiplicationRequired(): Boolean = rightMultiplication
}