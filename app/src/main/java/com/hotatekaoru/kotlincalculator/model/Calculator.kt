package com.hotatekaoru.kotlincalculator.model

import com.hotatekaoru.kotlincalculator.enum.OperationTypeEnum

class Calculator(private val formula: String) {
    private val numberStringList = ArrayList<String>()
    private val numberList = ArrayList<Double>()
    private val operationList = ArrayList<OperationTypeEnum>()
    private var isLastCharOperation = true

    fun call(): Double {
        // formulaを、numberStringListとoperationListに格納
        setFormulaToLists()

        // numberStringListをDouble型に変換してnumberListに格納。その際、引き算がある場合はその後の数値を-1倍する
        convertToNumberList()

        // 掛け算・割り算を実施
        calcMultiplicationAndDivision()

        // 足し算・引き算を実施
        return calcAddAndMinus()
    }

    /**
     * 数値部分をnumberListに格納
     * 計算式部分をoperationListに格納
     */
    private fun setFormulaToLists() {
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
                    if (OperationTypeEnum.MINUS.label == char.toString() && numberStringList.isEmpty()) {
                        numberStringList.add("0")
                    }
                    OperationTypeEnum.values().firstOrNull { it.label == char.toString() }?.let {
                        operationList.add(it)
                    }
                    isLastCharOperation = true
                }
            }
        }
    }

    private fun convertToNumberList() {
        for ((index, string) in numberStringList.withIndex()) {
            if (index == 0 || OperationTypeEnum.MINUS != operationList[index - 1]) {
                numberList.add(string.toDouble())
            } else {
                numberList.add(string.toDouble() * -1)
            }
        }
    }

    private fun calcMultiplicationAndDivision() {
        for ((index, operation) in operationList.withIndex()) {
            if (OperationTypeEnum.isPlusOrMinus(operation)) { continue }

            val value = operation.calc(numberList[index], numberList[index + 1])
            numberList[index] = 0.0
            numberList[index + 1] = value
        }
    }

    private fun calcAddAndMinus(): Double {
        return numberList.sum()
    }
}
