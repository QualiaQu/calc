package com.example.calc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

    private lateinit var inputField: TextView
    private var currentInput = ""
    private var currentOperator = ""
    private var firstOperand = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.inputField)

        val buttons = listOf<Button>(
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8),
            findViewById(R.id.btn9),
            findViewById(R.id.btn0),
            findViewById(R.id.btnEnter),
            findViewById(R.id.btnClear),
            findViewById(R.id.btnAdd),
            findViewById(R.id.btnDivide),
            findViewById(R.id.btnBackspace),
            findViewById(R.id.btnMultiply),
            findViewById(R.id.btnPoint),
            findViewById(R.id.btnSub)
        )


        for (button in buttons) {
            button.setOnClickListener { onButtonClick(button) }
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when {
            buttonText.isDigitsOnly() -> {
                currentInput += buttonText
                updateInputField()
            }
            buttonText == "." && !currentInput.contains(".") -> {
                currentInput += buttonText
                updateInputField()
            }
            buttonText in arrayOf("+", "-", "*", "/") -> {
                if (currentInput.isNotEmpty()) {
                    firstOperand = currentInput.toDouble()
                    currentOperator = buttonText
                    currentInput = ""
                }
            }
            buttonText == "=" -> {
                if (currentInput.isNotEmpty()) {
                    val secondOperand = currentInput.toDouble()
                    val result = performOperation(firstOperand, secondOperand, currentOperator)
                    currentInput = result.toString()
                    updateInputField()
                }
            }
            buttonText == "C" -> {
                currentInput = ""
                currentOperator = ""
                firstOperand = 0.0
                updateInputField()
            }
            buttonText == "←" -> {
                if (currentInput.isNotEmpty()) {
                    currentInput = currentInput.dropLast(1)
                    updateInputField()
                }
            }
        }
    }

    private fun performOperation(first: Double, second: Double, operator: String): Double {
        return when (operator) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> first / second
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }

    private fun updateInputField() {
        inputField.setText(currentInput)
    }
}
