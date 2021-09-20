package com.example.calculator

import com.example.calculator.databinding.ActivityMainBinding
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.calculator.expression.Expression
import com.example.calculator.expression_part.*
import com.example.calculator.extentions.toBeautyString


class MainActivity : AppCompatActivity() {
    companion object {
        private var expression: Expression = Expression()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onButtonClick(view: View?) {
        when(view?.id){
            R.id.button_zero -> appendToExpression(NumberPart("0"))
            R.id.button_one -> appendToExpression(NumberPart("1"))
            R.id.button_two -> appendToExpression(NumberPart("2"))
            R.id.button_three -> appendToExpression(NumberPart("3"))
            R.id.button_four -> appendToExpression(NumberPart("4"))
            R.id.button_five -> appendToExpression(NumberPart("5"))
            R.id.button_six -> appendToExpression(NumberPart("6"))
            R.id.button_seven -> appendToExpression(NumberPart("7"))
            R.id.button_eight -> appendToExpression(NumberPart("8"))
            R.id.button_nine -> appendToExpression(NumberPart("9"))

            R.id.button_comma -> appendToExpression(NumberPart(",", "."))

            R.id.button_add -> appendToExpression(OperationSign("+"))
            R.id.button_subtract -> appendToExpression(OperationSign("-"))
            R.id.button_multiply -> appendToExpression(OperationSign("×","*"))
            R.id.button_divide -> appendToExpression(OperationSign("÷", "/"))

            R.id.button_sqrt -> appendToExpression(ComplexOperation("√(", "sqrt(", true, false))
            R.id.button_factorial -> appendToExpression(ComplexOperation("!", false, true))
            R.id.button_bracket1 -> appendToExpression(ComplexOperation("(", true, false))
            R.id.button_bracket2 -> appendToExpression(ComplexOperation(")", false, true))
            R.id.button_ln -> appendToExpression(ComplexOperation("ln(", true, false))
            R.id.button_lg -> appendToExpression(ComplexOperation("lg(", true, false))
            R.id.button_abs -> appendToExpression(ComplexOperation("abs(", true, false))
            R.id.button_pi -> appendToExpression(ComplexOperation("π", "pi", true, true))
            R.id.button_power -> appendToExpression(ComplexOperation("^"))
            R.id.button_reverse -> appendToExpression(ComplexOperation("^(-1)", false, true))
            R.id.button_power_two -> appendToExpression(ComplexOperation("^2"))
            R.id.button_power_three -> appendToExpression(ComplexOperation("^3"))
            R.id.button_sin -> appendToExpression(ComplexOperation("sin(", true, false))
            R.id.button_cos -> appendToExpression(ComplexOperation("cos(",true, false))
            R.id.button_tan -> appendToExpression(ComplexOperation("tan(",true, false))
            R.id.button_cot -> appendToExpression(ComplexOperation("cot(",true, false))
            R.id.button_asin -> appendToExpression(ComplexOperation("arcsin(",true, false))
            R.id.button_acos -> appendToExpression(ComplexOperation("arccos(",true, false))
            R.id.button_atan -> appendToExpression(ComplexOperation("arctan(",true, false))
            R.id.button_acot -> appendToExpression(ComplexOperation("arccot(",true, false))
            R.id.button_e -> appendToExpression(ComplexOperation("e",true, true))

            R.id.button_clear -> {
                expression.clear()
                updateExpressionField()
            }
            R.id.button_backspace -> {
                expression.backspace()
                updateExpressionField()
            }
            R.id.button_percent -> {
                expression.percent()
                updateExpressionField()
            }
            R.id.button_result -> {
                updateExpressionField(true)
            }

            R.id.button_change -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment)
                if (currentFragment is SpecificButtonsFragment)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment, BasicButtonsFragment()).commit()
                else
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment, SpecificButtonsFragment()).commit()
            }
        }
    }

    private fun appendToExpression(expressionPart: ExpressionPart) {
        expression.append(expressionPart)
        updateExpressionField()
    }

    private fun updateExpressionField(hard: Boolean = false){
        binding.textResult.typeface = if (hard) Typeface.DEFAULT_BOLD else Typeface.DEFAULT

        if (hard && expression.isHardSolved()){
            expression.resultToExpression()
            binding.textResult.typeface = Typeface.DEFAULT
        }

        binding.textExpression.text = expression.getBeautyExpression()
        val expressionResult = expression.getResult(hard)
        if (expressionResult.isValid) {
            when {
                expressionResult.value.isNaN() -> {
                    binding.textResult.text = if (hard) "Math error" else ""
                }
                expressionResult.value == Double.POSITIVE_INFINITY -> {
                    binding.textResult.text = "∞"
                }
                expressionResult.value == Double.NEGATIVE_INFINITY -> {
                    binding.textResult.text = "-∞"
                }
                else -> {
                    binding.textResult.text = "=".plus(expressionResult.value.toBeautyString().replace('.', ','))
                }
            }
        }
        else{
            binding.textResult.text = if (hard) "Syntax error" else ""
        }
    }
}
