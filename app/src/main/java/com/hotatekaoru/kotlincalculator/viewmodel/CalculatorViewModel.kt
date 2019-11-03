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

    /**
     * 計算中かどうかのフラグ
     * = をクリックしたあと（計算後）の場合、次にタップされた値でmainValueTextを初期化する
     * 計算中の場合、次にタップされた値をmainValueTextの末尾にくっつける
     */
    private var calculating = false

    fun tapNumber(number: Number) {
        constructFormula(number.toString())
    }

    fun tapDot() {
        constructFormula(".")
    }

    fun tapPlus() {
        constructFormula(OperationTypeEnum.PLUS.string)
    }

    fun tapMinus() {
        constructFormula(OperationTypeEnum.MINUS.string)
    }

    fun tapMultiple() {
        constructFormula(OperationTypeEnum.MULTIPLE.string)
    }

    fun tapDivide() {
        constructFormula(OperationTypeEnum.DIVIDE.string)
    }

    fun tapClear() {
        mainValueText.minusLastCharacter()
    }

    fun tapEqual() {
        calculating = false
        mainValueText.set(calcurateValue().toString())
        supplementaryValueText.set("")
    }

    /**
     * 入力中の数値・計算式を配列に追加する（TODO）
     * 計算式として誤っている場合は、この処理を呼び出さない
     */
    private fun constructFormula(string: String) {
        if (calculating) {
            mainValueText.plus(string)
        } else {
            mainValueText.set(string)
            calculating = true
        }
    }

    private fun calcurateValue(): Double {
        // TODO: 計算を行う
        return 1.23
    }
}
