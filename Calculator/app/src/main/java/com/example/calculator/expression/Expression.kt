package com.example.calculator.expression

import com.example.calculator.expression_part.ExpressionPart
import com.example.calculator.expression_part.NumberPart
import com.example.calculator.expression_part.OperationSign
import org.mariuszgromada.math.mxparser.Function
import org.mariuszgromada.math.mxparser.Expression as MExpression

class Expression {
    private var lg: Function = Function("lg(x) = ln(x) / ln(10)")

    private var content: MutableList<ExpressionPart> = mutableListOf()

    fun getBeautyExpression(): String {
        var expression = ""
        for (expressionPart in content){
            expression += expressionPart.getBeautyContent()
        }
        return expression
    }

    fun append(expressionPart: ExpressionPart){
        if (expressionPart is OperationSign && content.lastOrNull() is OperationSign){
            content[content.size - 1] = expressionPart
        }
        else {
            content.add(expressionPart)
        }
    }

    fun clear(){
        content.clear()
    }

    fun backspace(){
        content.removeLastOrNull()
    }

    fun percent() {
        TODO("Not yet implemented")
    }

    fun getResult(): ExpressionResult {
        if (content.size == 0){
            return ExpressionResult(0.0, true)
        }
        else {
            var expressionString: String = ""
            for (expressionPart in content) {
                expressionString += expressionPart.getInternalString()
            }
            var depth = 0
            for (character in expressionString){
                when (character){
                    '(' -> depth++
                    ')' -> depth--
                }
            }
            if (depth > 0)
                expressionString += ")".repeat(depth)
            return getResult(expressionString)
        }
    }

    private fun getResult(expressionString: String): ExpressionResult{
        val mExpression = MExpression(expressionString, lg)
        val result = mExpression.calculate()
        val isValid = mExpression.checkLexSyntax()
        return ExpressionResult(result, isValid)
    }
}
