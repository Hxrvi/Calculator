package com.example.calculater

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.HapticFeedbackConstants
import android.view.View
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var previousCalculationTextView: TextView

    private var firstNumber: Double = 0.0
    private var operator: String = ""
    private var isNewOperation: Boolean = true

    private val decimalFormat = DecimalFormat("#.##########")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        previousCalculationTextView = findViewById(R.id.previousCalculationTextView)

        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)


        val btnPlus: Button = findViewById(R.id.btnPlus)
        val btnMinus: Button = findViewById(R.id.btnMinus)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnDivide: Button = findViewById(R.id.btnDivide)


        val btnDot: Button = findViewById(R.id.btnDot)
        val btnPercent: Button = findViewById(R.id.btnPercent)


        val btnEqual: Button = findViewById(R.id.btnEqual)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnBackspace: Button = findViewById(R.id.btnBackspace)


        btn0.setOnClickListener {
            haptic(it)
            appendNumber("0")
        }
        btn1.setOnClickListener {
            haptic(it)
            appendNumber("1")
        }
        btn2.setOnClickListener {
            haptic(it)
            appendNumber("2")
        }
        btn3.setOnClickListener {
            haptic(it)
            appendNumber("3")
        }
        btn4.setOnClickListener {
            haptic(it)
            appendNumber("4")
        }
        btn5.setOnClickListener {
            haptic(it)
            appendNumber("5")
        }
        btn6.setOnClickListener {
            haptic(it)
            appendNumber("6")
        }
        btn7.setOnClickListener {
            haptic(it)
            appendNumber("7")
        }
        btn8.setOnClickListener {
            haptic(it)
            appendNumber("8")
        }
        btn9.setOnClickListener {
            haptic(it)
            appendNumber("9")
        }
        btnDot.setOnClickListener {
            haptic(it)
            appendNumber(".")
        }



        btnPlus.setOnClickListener {
            haptic(it)
            setOperation("+")
        }
        btnMinus.setOnClickListener {
            haptic(it)
            setOperation("-")
        }
        btnMultiply.setOnClickListener {
            haptic(it)
            setOperation("*")
        }
        btnDivide.setOnClickListener {
            haptic(it)
            setOperation("÷")
        }
        btnPercent.setOnClickListener {
            haptic(it)
            setOperation("%")
        }

        btnEqual.setOnClickListener {
            haptic(it)
            calculateResult()
        }
        btnClear.setOnClickListener {
            haptic(it)
            clearCalculator()
        }
        btnBackspace.setOnLongClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            clearCalculator()
            true
        }

    }

    private fun haptic(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }


    private fun formatResult(number: Double): String {
        return decimalFormat.format(number)
    }

    private fun setOperation(operation: String) {

        if (resultTextView.text.toString() == "Error") return

        val current = resultTextView.text.toString().toDoubleOrNull() ?: return

        firstNumber = current
        operator = operation
        isNewOperation = true

        previousCalculationTextView.text =
            "${formatResult(firstNumber)} $operator"
    }

    private fun calculateResult() {

        if (resultTextView.text.toString() == "Error") return

        if (operator.isEmpty()) return

        try {
            val secondNumber = resultTextView.text.toString().toDouble()

            // Check division by zero
            if (operator == "÷" && secondNumber == 0.0) {
                resultTextView.text = "Error"
                previousCalculationTextView.text =
                    "${formatResult(firstNumber)} ÷ 0 ="
                isNewOperation = true
                return
            }

            val result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "÷" -> firstNumber / secondNumber
                "%" -> firstNumber % secondNumber
                else -> 0.0
            }

            previousCalculationTextView.text =
                "${formatResult(firstNumber)} $operator ${formatResult(secondNumber)} ="

            resultTextView.text = formatResult(result)
            previousCalculationTextView.text =
                "${formatResult(firstNumber)} $operator ${formatResult(secondNumber)} ="

            firstNumber = result
            operator = ""
            isNewOperation = true
            operator = ""
            firstNumber = result

        } catch (e: Exception) {
            resultTextView.text = "Error"
        }
    }

    private fun clearCalculator() {
        resultTextView.text = "0"
        previousCalculationTextView.text = ""
        isNewOperation = true
        firstNumber = 0.0
        operator = ""
    }

    private fun backspaceCalculator() {
        val currentText = resultTextView.text.toString()

        if (currentText.length > 1) {
            resultTextView.text = currentText.dropLast(1)
        } else {
            resultTextView.text = "0"
        }
    }

    private fun appendNumber(number: String) {

        val currentText = resultTextView.text.toString()

        if (number == "." && currentText.contains(".")) {
            return
        }

        if (currentText.replace(".", "").length >= 16) {
            return
        }

        if (isNewOperation) {
            previousCalculationTextView.text = ""
            resultTextView.text = if (number == ".") "0." else number
            isNewOperation = false
        }
        else {
            resultTextView.append(number)
        }
    }
}
