package com.example.calculator.expression

import com.example.calculator.expression_part.ExpressionPart
import com.example.calculator.expression_part.NumberPart
import com.example.calculator.expression_part.OperationSign
import com.example.calculator.extentions.toBeautyString
import org.mariuszgromada.math.mxparser.Function
import org.mariuszgromada.math.mxparser.Expression as ParserExpression

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

    fun append(newExpressionPart: ExpressionPart){
        if (newExpressionPart is OperationSign && content.lastOrNull() is OperationSign){
            content[content.size - 1] = newExpressionPart
            return
        }

        if (newExpressionPart is NumberPart && newExpressionPart.getInternalString() != "."
            && content.lastOrNull() is NumberPart && content.last().getInternalString() == "0"
            && (content.size == 1 || content[content.size - 2] !is NumberPart)) content.removeLast()

        content.add(newExpressionPart)
    }

    fun clear(){
        content.clear()
    }

    fun backspace(){
        content.removeLastOrNull()
    }

    fun percent() {
        var newContent = content.toMutableList()
        var lastNumber = ""
        while (newContent.lastOrNull() is NumberPart){
            lastNumber = newContent.last().getInternalString() + lastNumber
            newContent.removeLast()
        }
        var expressionString: String = ""
        for (expressionPart in newContent) {
            if (expressionPart != newContent.last() || expressionPart !is OperationSign) {
                expressionString += expressionPart.getInternalString()
            }
        }
        var expressionResult = getResult(expressionString)
        if (expressionResult.isValid && expressionResult.value.isFinite()){
            var newLastNumber = (expressionResult.value * lastNumber.toDouble() / 100).toBeautyString()
            for (numberCharacter in newLastNumber){
                newContent.add(NumberPart(numberCharacter.toString()))
            }
            content = newContent
        }
    }

    fun getResult(): ExpressionResult {
        var expressionString: String = ""
        for (expressionPart in content) {
            if (expressionPart != content.last() || expressionPart !is OperationSign) {
                expressionString += expressionPart.getInternalString()
            }
        }
        if (expressionString.isEmpty()) {
            return ExpressionResult(0.0, true)
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

    private fun getResult(expressionString: String): ExpressionResult{
        val parserExpression = ParserExpression(expressionString, lg)
        val result = parserExpression.calculate()
        val isValid = parserExpression.checkLexSyntax()
        return ExpressionResult(result, isValid)
    }
}