package com.example.calculator.expression_part

class ComplexOperation(
    content: String,
    internalString: String,
    leftMultiplication: Boolean,
    rightMultiplication: Boolean) : ExpressionPart(content, internalString, leftMultiplication, rightMultiplication) {

    constructor(content: String, internalString: String) : this(content, internalString, false, false)

    constructor(content: String) : this(content, content, false, false)

    constructor(content: String, leftMultiplication: Boolean, rightMultiplication: Boolean)
            : this(content, content, leftMultiplication, rightMultiplication)

}