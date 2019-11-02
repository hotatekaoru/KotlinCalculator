package com.hotatekaoru.kotlincalculator.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.hotatekaoru.kotlincalculator.enum.OperationTypeEnum
import com.hotatekaoru.kotlincalculator.extension.minusLastCharacter
import com.hotatekaoru.kotlincalculator.extension.plus

class CalculatorViewModel : ViewModel() {

    /**
     * 入力中はtapされたボタンの内容をそのまま表示するText
     * 計算後（=を押されたあと）は計算結果を表示する
     */
    var mainValueText = ObservableField<String>("")

    /**
     * メインテキストの下に表示するテキスト
     * 計算中に値を表示し、計算後（=を押されたあと）は空にする
     */
    var supplementaryValueText = ObservableField<String>("")

    fun tapNumber(number: Number) {
        mainValueText.plus(number.toString())
    }

    fun tapDot() {
        mainValueText.plus(".")
    }

    fun tapPlus() {
        tapOperation(OperationTypeEnum.PLUS)
    }

    fun tapMinus() {
        tapOperation(OperationTypeEnum.MINUS)
    }

    fun tapMultiple() {
        tapOperation(OperationTypeEnum.MULTIPLE)
    }

    fun tapDivide() {
        tapOperation(OperationTypeEnum.DIVIDE)
    }

    fun tapClear() {
        mainValueText.minusLastCharacter()
    }

    fun tapEqual() {
        mainValueText.set(calcurateValue().toString())
        supplementaryValueText.set("")
    }

    private fun tapOperation(enum: OperationTypeEnum) {
        mainValueText.plus(enum.string)
    }

    private fun calcurateValue(): Double {
        // TODO: 計算を行う
        return 1.23
    }
}
