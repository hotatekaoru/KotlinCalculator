package com.hotatekaoru.kotlincalculator.model

import com.hotatekaoru.kotlincalculator.enum.OperationTypeEnum

class Calculator(private val formula: String) {
    private val numberList = ArrayList<String>()
    private val operationList = ArrayList<OperationTypeEnum>()
    private var isLastCharOperation = true

    fun call(): Double {
        // listに格納
        setFormulaToLists()

        // 掛け算・割り算を実施
        calcMultiplicationAndDivision()
        // 足し算・引き算を実施
        return 1.23
    }

    /**
     * 数値部分をnumberListに格納
     * 計算式部分をoperationListに格納
     */
    private fun setFormulaToLists() {
        formula.forEach { char ->
            // TODO: ここはもっといい書き方があるのかもしれない
            if (char in '0'..'9') {
                if (isLastCharOperation) {
                    numberList.add(char.toString())
                } else {
                    val newNumber = "${numberList.last()}$char"
                    numberList.removeAt(numberList.lastIndex)
                    numberList.add(newNumber)
                }
                isLastCharOperation = false
            } else if (char.toString() == ".") {
                if (isLastCharOperation) {
                    numberList.add("0.")
                } else {
                    val newNumber = "${numberList.last()}$char"
                    numberList.removeAt(numberList.lastIndex)
                    numberList.add(newNumber)
                }
                isLastCharOperation = false
            } else {
                // 最初にマイナスから始まる場合は、最初に0があたかもあるかのようにする。
                // これにより、numberList[0]のあとに、operationList[0]がある状態を作る
                if (char.equals(OperationTypeEnum.MINUS.label) && numberList.isEmpty()) {
                    numberList.add("0")
                }
                OperationTypeEnum.values().firstOrNull { it.label == char.toString() }?.let {
                    operationList.add(it)
                }
                isLastCharOperation = true
            }
        }
    }

    private fun calcMultiplicationAndDivision() {
    }
}
