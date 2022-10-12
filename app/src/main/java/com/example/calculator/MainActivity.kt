package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

//keys for storing
private const val STATE_PENDING_OPERATION = "pendingOperation"
private const val STATE_OPERAND1 = "operand1"


class MainActivity : AppCompatActivity() {
//    private lateinit var result : EditText
//    private lateinit var newNumber: EditText
//    private val operator by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.sign) }

    // Operands to hold the values and operation
    private var pendingOperation = "="
    private var operand1 : Double? = null
    private var operand2 : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
        // numbers and dot buttons
//        val button0 : Button = findViewById(R.id.button0)
//        val button1 : Button = findViewById(R.id.button1)
//        val button2 : Button = findViewById(R.id.button2)
//        val button3 : Button = findViewById(R.id.button3)
//        val button4 : Button = findViewById(R.id.button4)
//        val button5 : Button = findViewById(R.id.button5)
//        val button6 : Button = findViewById(R.id.button6)
//        val button7 : Button = findViewById(R.id.button7)
//        val button8 : Button = findViewById(R.id.button8)
//        val button9 : Button = findViewById(R.id.button9)
//        val dot : Button = findViewById(R.id.dot)

        //Creating Listener for calling operations of all numbers and dot

        val listener = View.OnClickListener {
            val b = it as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        dot.setOnClickListener(listener)

        //Creating instances for the Operations
//        val plus : Button= findViewById(R.id.plus)
//        val minus : Button= findViewById(R.id.minus)
//        val divide : Button= findViewById(R.id.divide)
//        val multiply : Button= findViewById(R.id.multiply)
//        val equal : Button= findViewById(R.id.equal)

        // Creating Listener for the Operations
        val opListener = View.OnClickListener{
            val op = (it as Button).text.toString()
            val value = newNumber.text.toString()
            if(value.isNotEmpty() && value != "."){
                performOperation(value, pendingOperation)
            }
            pendingOperation = op
            sign.text = pendingOperation
        }

        plus.setOnClickListener(opListener)
        minus.setOnClickListener(opListener)
        divide.setOnClickListener(opListener)
        multiply.setOnClickListener(opListener)
        equal.setOnClickListener(opListener)

        buttonBack.setOnClickListener {
            if (newNumber.textSize >= 1) {
                newNumber.text = newNumber.text.dropLast(1) as Editable?
            }
        }

        buttonClearAll.setOnClickListener {
            newNumber.setText("")
        }

    }

    private fun performOperation(value: String, op: String) {
        if(operand1 == null){
            operand1 = value.toDouble()
        }
        else{
            operand2 = value.toDouble()
            if(op == "="){
                pendingOperation = op
            }

            when(pendingOperation){
                "=" -> operand1 = operand2
                "/" -> operand1 = if(operand2 == 0.0){
                                        Double.NaN
                                } else {
                                    operand1!! / operand2
                                }
                "*" -> operand1 = operand1!! * operand2
                "-" -> operand1 = operand1!! - operand2
                "+" -> operand1 = operand1!! + operand2
            }

        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
        if(operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1!!)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sign.text = savedInstanceState.getString(STATE_PENDING_OPERATION)
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        pendingOperation = sign.text.toString()
    }
}