package com.example.calculator.expression

import com.example.calculator.expression_part.ExpressionPart

class Expression {
    private var content: MutableList<ExpressionPart> = mutableListOf()

    private var isValid: Boolean = true

    public fun isValid(): Boolean = isValid

    public fun getExpression(): String {
        var result: String = ""
        for (expressionPart in content){
            //result += expressionPart.content
        }
        return result
    }

    public fun getResult(): String {
        var expression: String = ""
        for (expressionPart in content){
            //expression += expressionPart.internalString
        }
        return expression
    }

    public fun append(expressionPart: ExpressionPart){
        content.add(expressionPart)
    }
}