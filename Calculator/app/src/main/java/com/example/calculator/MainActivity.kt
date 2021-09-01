package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.calculator.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var mathSymbols: Set<String> = setOf("*", "/", "+", "-")
    private var expression: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonZero.setOnClickListener { appendToExpression("0") }
        binding.buttonOne.setOnClickListener { appendToExpression("1") }
        binding.buttonTwo.setOnClickListener { appendToExpression("2") }
        binding.buttonThree.setOnClickListener { appendToExpression("3") }
        binding.buttonFour.setOnClickListener { appendToExpression("4") }
        binding.buttonFive.setOnClickListener { appendToExpression("5") }
        binding.buttonSix.setOnClickListener { appendToExpression("6") }
        binding.buttonSeven.setOnClickListener { appendToExpression("7") }
        binding.buttonEight.setOnClickListener { appendToExpression("8") }
        binding.buttonNine.setOnClickListener { appendToExpression("9") }
        binding.buttonComma.setOnClickListener { appendToExpression(",") }
        binding.buttonAdd.setOnClickListener { appendToExpression("+") }
        binding.buttonSubtract.setOnClickListener { appendToExpression("-") }
        binding.buttonMultiply.setOnClickListener { appendToExpression("*") }
        binding.buttonDivide.setOnClickListener { appendToExpression("/") }

        binding.buttonClear.setOnClickListener {
            expression.clear()
            updateExpressionField()
        }
        binding.buttonBackspace.setOnClickListener {
            expression.removeLastOrNull()
            updateExpressionField()
        }

        binding.buttonResult.setOnClickListener{

        }
    }

    private fun appendToExpression(str: String) {
        if (expression.lastOrNull() in mathSymbols && str in mathSymbols){
            expression[expression.size - 1] = str
        }
        else {
            expression.add(str)
        }
        updateExpressionField()
    }

    private fun updateExpressionField(){
        val expressionString: String = expression.joinToString("")
        val exp = Expression(expressionString)
        val res = exp.calculate()
        binding.textExpression.text = expressionString
        binding.textResult.text = res.toString() + exp.checkLexSyntax() + exp.checkSyntax()
    }
}