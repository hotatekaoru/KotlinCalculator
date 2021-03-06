package com.hotatekaoru.kotlincalculator.models

import com.hotatekaoru.kotlincalculator.enums.OperationType

// 計算式に関するロジックは、Wikiに記載
// https://github.com/hotatekaoru/KotlinCalculator/wiki/Logic-of-Calculator
class Calculator {
    private lateinit var formula: String

    private val numberStringList = ArrayList<String>()
    private val numberList = ArrayList<Double>()
    private val operationList = ArrayList<OperationType>()

    fun call(formula: String): Double {
        if (formula.isEmpty()) { return 0.0 }
        this.formula = formula

        // 初期化する
        init()

        // formulaを、numberStringListとoperationListに格納
        setFormulaToLists()

        // numberStringListをDouble型に変換してnumberListに格納。その際、引き算がある場合はその後の数値を-1倍する
        convertToNumberList()

        // 掛け算・割り算を実施
        calcMultiplicationAndDivision()

        // 足し算・引き算を実施
        return calcAddAndMinus()
    }

    private fun init() {
        numberStringList.clear()
        numberList.clear()
        operationList.clear()
    }

    /**
     * 数値部分をnumberListに格納
     * 計算式部分をoperationListに格納
     */
    private fun setFormulaToLists() {
        // forEach内で、最後に扱った文字列が四則演算である場合true
        var isLastCharOperation = true

        formula.forEach { char ->
            when {
                char in '0'..'9' -> {
                    if (isLastCharOperation) {
                        numberStringList.add(char.toString())
                    } else {
                        val newNumber = "${numberStringList.last()}$char"
                        numberStringList.removeAt(numberStringList.lastIndex)
                        numberStringList.add(newNumber)
                    }
                    isLastCharOperation = false
                }
                char.toString() == "." -> {
                    if (isLastCharOperation) {
                        numberStringList.add("0.")
                    } else {
                        val newNumber = "${numberStringList.last()}$char"
                        numberStringList.removeAt(numberStringList.lastIndex)
                        numberStringList.add(newNumber)
                    }
                    isLastCharOperation = false
                }
                else -> {
                    // 最初にマイナスから始まる場合は、最初に0があたかもあるかのようにする。
                    // これにより、numberList[0]のあとに、operationList[0]がある状態を作る
                    if (OperationType.MINUS.label == char.toString() && numberStringList.isEmpty()) {
                        numberStringList.add("0")
                    }
                    OperationType.values().firstOrNull { it.label == char.toString() }?.let {
                        operationList.add(it)
                    }
                    isLastCharOperation = true
                }
            }
        }

        // 最後が演算子で終わる場合は、最後の演算子を計算式に入れない
        if (numberStringList.size == operationList.size) {
            operationList.removeAt(operationList.size - 1)
        }
    }

    private fun convertToNumberList() {
        for ((index, string) in numberStringList.withIndex()) {
            if (index == 0 || OperationType.MINUS != operationList[index - 1]) {
                numberList.add(string.toDouble())
            } else {
                numberList.add(string.toDouble() * -1)
            }
        }
    }

    private fun calcMultiplicationAndDivision() {
        for ((index, operation) in operationList.withIndex()) {
            if (OperationType.isPlusOrMinus(operation)) { continue }

            val value = operation.calc(numberList[index], numberList[index + 1])
            numberList[index] = 0.0
            numberList[index + 1] = value
        }
    }

    private fun calcAddAndMinus(): Double {
        return numberList.sum()
    }
}
