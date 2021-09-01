package com.example.calculator.expression_part

class ComplexOperation(content: String, internalString: String) : Operation(content, internalString) {
    constructor(content: String) : this(content, content)
}