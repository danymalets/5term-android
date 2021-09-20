package com.example.calculator.expression_part

class OperationSign(content: String, internalString: String) : ExpressionPart(content, internalString, false, false) {
    constructor(content: String) : this(content, content)
}