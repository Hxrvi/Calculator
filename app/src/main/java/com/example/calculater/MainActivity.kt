package com.example.calculater

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var previousCalculationTextView: TextView

    private var firstNumber: Double = 0.0
    private var operator: String = ""
    private var isNewOperation: Boolean = true

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


        btn0.setOnClickListener {appendNumber("0")}
        btn1.setOnClickListener {appendNumber("1")}
        btn2.setOnClickListener {appendNumber("2")}
        btn3.setOnClickListener {appendNumber("3")}
        btn4.setOnClickListener {appendNumber("4")}
        btn5.setOnClickListener {appendNumber("5")}
        btn6.setOnClickListener {appendNumber("6")}
        btn7.setOnClickListener {appendNumber("7")}
        btn8.setOnClickListener {appendNumber("8")}
        btn9.setOnClickListener {appendNumber("9")}
        btnDot.setOnClickListener {appendNumber(".")}



        btnPlus.setOnClickListener {setOperation("+")}
        btnMinus.setOnClickListener {setOperation("-")}
        btnMultiply.setOnClickListener {setOperation("*")}
        btnDivide.setOnClickListener {setOperation("÷")}
        btnPercent.setOnClickListener {setOperation("%")}

        btnEqual.setOnClickListener {calculateResult()}
        btnClear.setOnClickListener {clearCalculator()}
        btnBackspace.setOnClickListener {backspaceCalculator()}

    }

    private fun setOperation(operation: String){
        firstNumber = resultTextView.text.toString().toDouble()
        operator = operation
        isNewOperation = true
        previousCalculationTextView.text = "$firstNumber $operator"
    }


    private fun calculateResult(){
        try {
            val secondNumber = resultTextView.text.toString().toDouble()
            var result = when(operator){
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "÷" -> firstNumber / secondNumber
                "%" -> firstNumber % secondNumber
                else -> 0.0
            }

            previousCalculationTextView.text = "$firstNumber $operator $secondNumber="
            resultTextView.text = result.toString()
            isNewOperation = true
        }
        catch (e: Exception){
            resultTextView.text = "Error"
        }
    }

    private fun clearCalculator(){
        resultTextView.text = "0"
        previousCalculationTextView.text = ""
        isNewOperation = true
        firstNumber = 0.0
        operator = ""
    }

    private fun backspaceCalculator(){
        val currentText = resultTextView.text.toString()
        if(currentText.length > 1){
            resultTextView.text = currentText.substring(0, currentText.length - 1)
        }
        else{
            resultTextView.text = "0"
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun appendNumber(number: String){
        if(isNewOperation){
            resultTextView.text = number
            isNewOperation = false
        }
        else{
            resultTextView.text="${resultTextView.text}$number"
        }
    }
}