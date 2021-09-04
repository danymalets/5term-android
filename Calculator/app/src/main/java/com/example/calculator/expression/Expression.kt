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

        if (newExpressionPart.getInternalString() == "." && content.lastOrNull() !is NumberPart){
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
        val newContent = content.toMutableList()
        var lastNumber = ""
        while (newContent.lastOrNull() is NumberPart){
            lastNumber = newContent.last().getInternalString() + lastNumber
            newContent.removeLast()
        }
        if (lastNumber == ""){
            return
        }
        val expressionResult = getResult(newContent)
        val newLastNumber: String

        val expressionPercentage = newContent.size > 1 && (newContent.last().getInternalString() in "-+")

        newLastNumber = if (expressionPercentage && expressionResult.isValid && expressionResult.value.isFinite()){
            (expressionResult.value * lastNumber.toDouble() / 100).toBeautyString()
        } else{
            (lastNumber.toDouble() / 100).toBeautyString()
        }

        for (numberCharacter in newLastNumber){
            when (numberCharacter){
                '-' -> {
                    if (newContent.size == 0 || newContent.last().getInternalString() !in "-+"){
                        throw UnsupportedOperationException()
                    }
                    when (newContent.last().getInternalString()){
                        "-" -> newContent[newContent.size - 1] = OperationSign("+")
                        "+" -> newContent[newContent.size - 1] = OperationSign("-")
                    }
                }
                '.' -> newContent.add(NumberPart(",", "."))
                else -> newContent.add(NumberPart(numberCharacter.toString()))
            }
        }
        content = newContent
    }

    fun getResult(): ExpressionResult {
        return getResult(content)
    }

    private fun getResult(expression: MutableList<ExpressionPart>): ExpressionResult{
        var expressionString = ""
        for (expressionPart in expression) {
            if (expressionPart != expression.last() || expressionPart !is OperationSign) {
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
        val parserExpression = ParserExpression(expressionString, lg)
        val result = parserExpression.calculate()
        val isValid = parserExpression.checkLexSyntax()
        return ExpressionResult(result, isValid)
    }
}