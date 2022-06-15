package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    // Textview for the input to the calculator
    private var tvInput: TextView? = null

    // Initializing variables to check the decimal point
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tv_result)
    }

    // OnDigit (when digit is pressed) method
    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    // CLR button method
    fun onCLR(view: View) {
        tvInput?.text = ""
    }

    // Method to add a decimal point
    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    // if text is not empty, was a number, and no operator is added,
    // add operator if button is clicked on
    fun onOperator(view: View) {
        tvInput?.text?.let {
        if(lastNumeric && !isOperatorAdded(it.toString())) {
            tvInput?.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
        }
    }

    // method to check if operator is added
    private fun isOperatorAdded(value: String): Boolean {
        // ignoring subtraction at the beginning because negatives are possible
        // otherwise, return true saying an operator is added
        return if(value.startsWith("-")){
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    // method to remove the ".0" off of a string in the input if whole number
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }


    // method for equal operator to check if the value is equal or not
    // if last input was a number
    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            // Going to use a split method for subtraction to split the values in the input
            try {
                // calling a prefix fo we can continue to subtract even after equating a value
                // if the value is negative, remove it at first before the calculation
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                // split() built in to the library
                var splitValue = tvValue.split("-")
                var one = splitValue[0]
                var two = splitValue[1]

                    // reinitializing the prefix for subtraction to 1
                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    // lastly call our method to remove zero if a whole number
                tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if(tvValue.contains("+")){
                    // split() built in to the library
                    var splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    // reinitializing the prefix for addition to 1
                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if(tvValue.contains("*")){
                    // split() built in to the library
                    var splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    // reinitializing the prefix for multiplication to 1
                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                } else if(tvValue.contains("/")){
                    // split() built in to the library
                    var splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    // reinitializing the prefix for division to 1
                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            } catch (e: ArithmeticException) { // catch math exception, print to LOG
                e.printStackTrace()
            }
        }
    }

}