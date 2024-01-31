package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var result: TextView
    private var firstNumer = ""
    private var secondNumber = ""
    private var resultNumber = ""
    private var operator = ""
    private var showText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.result)

        findViewById<Button>(R.id.b0).setOnClickListener(this)
        findViewById<Button>(R.id.b1).setOnClickListener(this)
        findViewById<Button>(R.id.b2).setOnClickListener(this)
        findViewById<Button>(R.id.b3).setOnClickListener(this)
        findViewById<Button>(R.id.b4).setOnClickListener(this)
        findViewById<Button>(R.id.b5).setOnClickListener(this)
        findViewById<Button>(R.id.b6).setOnClickListener(this)
        findViewById<Button>(R.id.b7).setOnClickListener(this)
        findViewById<Button>(R.id.b8).setOnClickListener(this)
        findViewById<Button>(R.id.b9).setOnClickListener(this)

        findViewById<Button>(R.id.add).setOnClickListener(this)
        findViewById<Button>(R.id.subs).setOnClickListener(this)
        findViewById<Button>(R.id.multiply).setOnClickListener(this)
        findViewById<Button>(R.id.divide).setOnClickListener(this)
        findViewById<Button>(R.id.reciprocal).setOnClickListener(this)
        findViewById<Button>(R.id.sqrt).setOnClickListener(this)
        findViewById<Button>(R.id.ce).setOnClickListener(this)
        findViewById<Button>(R.id.clean).setOnClickListener(this)
        findViewById<Button>(R.id.equal).setOnClickListener(this)
        findViewById<Button>(R.id.dot).setOnClickListener(this)
    }

    private fun isDoubleOperator(id: Int): Boolean {
        return id == R.id.add || id == R.id.subs || id == R.id.multiply || id == R.id.divide
    }

    private fun isNumber(id: Int): Boolean {
        val idSet: HashSet<Int> = hashSetOf(
            R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5,
            R.id.b6, R.id.b7, R.id.b8, R.id.b9
        )
        return idSet.contains(id)
    }

    private fun isSingleOperator(id: Int): Boolean {
        return id == R.id.reciprocal || id == R.id.sqrt
    }

    private fun calculate(): String {
        val ans: Double = when (operator) {
            getString(R.string.add) -> firstNumer.toDouble() + secondNumber.toDouble()
            getString(R.string.subs) -> firstNumer.toDouble() - secondNumber.toDouble()
            getString(R.string.multiply) -> firstNumer.toDouble() * secondNumber.toDouble()
            getString(R.string.divide) -> firstNumer.toDouble() / secondNumber.toDouble()
            getString(R.string.sqrt) -> sqrt(firstNumer.toDouble())
            getString(R.string.reciprocal) -> 1 / firstNumer.toDouble()
            else -> 0.0
        }
        return ans.toString()
    }


    override fun onClick(v: View?) {
        val input = (v as Button).text

        if (isDoubleOperator(v.id)) {
            if (firstNumer.isNotEmpty() && operator.isEmpty()) {
                operator = input as String
                showText += operator
            }
            if (firstNumer.isNotEmpty() && operator.isNotEmpty() && secondNumber.isNotEmpty()) {
                resultNumber = calculate()
                firstNumer = resultNumber
                secondNumber = ""
                operator = input as String
                showText += "=$firstNumer$operator"
            }
        } else if (isNumber(v.id)) {
            if (operator.isEmpty()) {
                if (resultNumber.isNotEmpty()) {
                    firstNumer = input as String
                    resultNumber = ""
                } else firstNumer += input
            } else {
                secondNumber += input
            }
            showText += input
        } else if (isSingleOperator(v.id)) {
            if (firstNumer.isNotEmpty() && operator.isEmpty()) {
                operator = input as String
                resultNumber = calculate()
                firstNumer = resultNumber
                operator = ""
                showText += if (v.id == R.id.sqrt) getString(R.string.sqrt) + "=" + resultNumber
                else "/=$resultNumber"
            }
        } else if (v.id == R.id.clean) {
            firstNumer = ""
            operator = ""
            secondNumber = ""
            showText = ""
        } else if (v.id == R.id.equal) {
            if (firstNumer.isNotEmpty() && operator.isNotEmpty() && secondNumber.isNotEmpty()) {
                resultNumber = calculate()
                firstNumer = resultNumber
                operator = ""
                secondNumber = ""
                showText += "=$firstNumer"
            }
        }
        result.text = showText
    }
}