package com.example.calculator

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.expression.Expression
import com.example.calculator.extentions.toBeautyString
import com.example.calculator.expression_part.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var expression: Expression = Expression()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonZero.setOnClickListener { appendToExpression(NumberPart("0")) }
        binding.buttonOne.setOnClickListener { appendToExpression(NumberPart("1")) }
        binding.buttonTwo.setOnClickListener { appendToExpression(NumberPart("2")) }
        binding.buttonThree.setOnClickListener { appendToExpression(NumberPart("3")) }
        binding.buttonFour.setOnClickListener { appendToExpression(NumberPart("4")) }
        binding.buttonFive.setOnClickListener { appendToExpression(NumberPart("5")) }
        binding.buttonSix.setOnClickListener { appendToExpression(NumberPart("6")) }
        binding.buttonSeven.setOnClickListener { appendToExpression(NumberPart("7")) }
        binding.buttonEight.setOnClickListener { appendToExpression(NumberPart("8")) }
        binding.buttonNine.setOnClickListener { appendToExpression(NumberPart("9")) }

        binding.buttonComma.setOnClickListener { appendToExpression(NumberPart(",", ".")) }

        binding.buttonAdd.setOnClickListener { appendToExpression(OperationSign("+")) }
        binding.buttonSubtract.setOnClickListener { appendToExpression(OperationSign("-")) }
        binding.buttonMultiply.setOnClickListener { appendToExpression(OperationSign("×","*")) }
        binding.buttonDivide.setOnClickListener { appendToExpression(OperationSign("÷", "/")) }

        binding.buttonSqrt?.setOnClickListener { appendToExpression(ComplexOperation("sqrt(")) }
        binding.buttonFactorial?.setOnClickListener { appendToExpression(ComplexOperation("!")) }
        binding.buttonBracket1?.setOnClickListener { appendToExpression(ComplexOperation("(")) }
        binding.buttonBracket2?.setOnClickListener { appendToExpression(ComplexOperation(")")) }
        binding.buttonLn?.setOnClickListener { appendToExpression(ComplexOperation("ln(")) }
        binding.buttonLg?.setOnClickListener { appendToExpression(ComplexOperation("lg(")) }
        binding.buttonAbs?.setOnClickListener { appendToExpression(ComplexOperation("abs(")) }
        binding.buttonPi?.setOnClickListener { appendToExpression(ComplexOperation("π", "pi")) }
        binding.buttonPower?.setOnClickListener { appendToExpression(ComplexOperation("^")) }
        binding.buttonReverse?.setOnClickListener { appendToExpression(ComplexOperation("^(-1)")) }
        binding.buttonPowerTwo?.setOnClickListener { appendToExpression(ComplexOperation("^2")) }
        binding.buttonPowerThree?.setOnClickListener { appendToExpression(ComplexOperation("^3")) }
        binding.buttonSin?.setOnClickListener { appendToExpression(ComplexOperation("sin(")) }
        binding.buttonCos?.setOnClickListener { appendToExpression(ComplexOperation("cos(")) }
        binding.buttonTan?.setOnClickListener { appendToExpression(ComplexOperation("tan(")) }
        binding.buttonCot?.setOnClickListener { appendToExpression(ComplexOperation("cot(")) }
        binding.buttonAsin?.setOnClickListener { appendToExpression(ComplexOperation("arcsin(")) }
        binding.buttonAcos?.setOnClickListener { appendToExpression(ComplexOperation("arccos(")) }
        binding.buttonAtan?.setOnClickListener { appendToExpression(ComplexOperation("arctan(")) }
        binding.buttonAcot?.setOnClickListener { appendToExpression(ComplexOperation("arccot(")) }

        binding.buttonE?.setOnClickListener { appendToExpression(ComplexOperation("e")) }

        binding.buttonClear.setOnClickListener {
            expression.clear()
            updateExpressionField()
        }
        binding.buttonBackspace.setOnClickListener {
            expression.backspace()
            updateExpressionField()
        }

        binding.buttonPercent.setOnClickListener {
            expression.percent()
            updateExpressionField()
        }

        binding.buttonResult.setOnClickListener{
            updateExpressionField(true)
        }
    }

    private fun appendToExpression(expressionPart: ExpressionPart) {
        expression.append(expressionPart)
        updateExpressionField()
    }

    private fun updateExpressionField(hard: Boolean = false){
        binding.textResult.typeface = if (hard) Typeface.DEFAULT_BOLD else Typeface.DEFAULT

        binding.textExpression.text = expression.getBeautyExpression()
        val expressionResult = expression.getResult()
        if (expressionResult.isValid) {
            if (expressionResult.value.isNaN()){
                binding.textResult.text = if (hard) "Math error" else ""
            }
            else if (expressionResult.value == Double.POSITIVE_INFINITY){
                binding.textResult.text = "∞"
            }
            else if (expressionResult.value == Double.NEGATIVE_INFINITY){
                binding.textResult.text = "-∞"
            }
            else {
                binding.textResult.text =
                    "=" + expressionResult.value.toBeautyString().replace('.', ',')
            }
        }
        else{
            binding.textResult.text = if (hard) "Syntax error" else ""
        }
    }
}
