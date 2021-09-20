package com.example.calculator.expression_part

class NumberPart(content: String, internalString: String) : ExpressionPart(content, internalString, true, true) {
    constructor(content: String) : this(content, content)
}