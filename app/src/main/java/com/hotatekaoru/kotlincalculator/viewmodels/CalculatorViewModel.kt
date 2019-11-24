package com.hotatekaoru.kotlincalculator.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.hotatekaoru.kotlincalculator.enums.OperationType
import com.hotatekaoru.kotlincalculator.extensions.minusLastCharacter
import com.hotatekaoru.kotlincalculator.extensions.plus
import com.hotatekaoru.kotlincalculator.extensions.takeLast
import com.hotatekaoru.kotlincalculator.models.CalculateValueFormatter
import com.hotatekaoru.kotlincalculator.models.Calculator

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
    var calculating = ObservableBoolean(false)

    /**
     * 数字をタップされた際の処理
     * calculatingがtrueの場合、mainValueTextの末尾に入力された数字を加える
     * calculatingがfalseの場合、mainValueTextを入力された数字に入れ替える
     */
    fun tapNumber(number: Number) {
        if (calculating.get()) {
            mainValueText.plus(number.toString())
        } else {
            mainValueText.set(number.toString())
            calculating.set(true)
        }
        supplementaryValueText.set(calculateValue())
    }

    /**
     * 小数点をタップされた際の処理
     * mainValueTextの四則演算子以降に小数点が既にある場合、return
     * 上記以外の場合、末尾に.を加える
     */
    fun tapDot() {
        mainValueText.get()?.let { text ->
            val operationIndex = text.lastIndexOfAny(OperationType.values().map { it.label })
            if (text.substring(operationIndex + 1).contains(".")) { return }
        }

        mainValueText.plus(".")
        calculating.set(true)
    }

    /**
     * 四則演算子をタップされた際の処理
     */
    fun tapOperation(operationType: OperationType) {
        val valid = when (operationType) {
            OperationType.PLUS -> { tapPlus() }
            OperationType.MINUS -> { tapMinus() }
            OperationType.MULTIPLE -> { tapMultiple() }
            OperationType.DIVIDE -> { tapDivide() }
        }

        if (valid) { supplementaryValueText.set(calculateValue()) }
    }

    /**
     * +をタップされた際の処理
     * mainValueTextが空の場合、return
     * mainValueTextの末尾が.の場合、return
     * mainValueTextが-のみの場合、空に更新してreturn
     * mainValueTextの末尾が四則演算子の場合、+に入れ替える
     * 上記以外の場合、mainValueTextの末尾に+を追加
     */
    private fun tapPlus(): Boolean {
        if (mainValueText.get().isNullOrBlank()) { return false }
        if (mainValueText.takeLast(1) == ".") { return false }
        if (mainValueText.get() != null && mainValueText.get().equals(OperationType.MINUS.label)) {
            mainValueText.set("")
            calculating.set(false)
            return false
        }

        if (OperationType.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationType.PLUS.label)
        calculating.set(true)
        return true
    }

    /**
     * -をタップされた際の処理
     * mainValueTextの末尾が.の場合、return
     * mainValueTextの末尾が四則演算子の場合、-に入れ替える
     * 上記以外の場合、mainValueTextの末尾に-を追加
     */
    private fun tapMinus(): Boolean {
        if (mainValueText.takeLast(1) == ".") { return false }
        if (OperationType.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationType.MINUS.label)
        calculating.set(true)
        return true
    }

    /**
     * ×をタップされた際の処理
     * mainValueTextが空の場合、return
     * mainValueTextの末尾が.の場合、return
     * mainValueTextが-のみの場合、空に更新してreturn
     * mainValueTextの末尾が四則演算子の場合、×に入れ替える
     * 上記以外の場合、mainValueTextの末尾に×を追加
     */
    private fun tapMultiple(): Boolean {
        if (mainValueText.get().isNullOrBlank()) { return false }
        if (mainValueText.takeLast(1) == ".") { return false }
        if (mainValueText.get() != null && mainValueText.get().equals(OperationType.MINUS.label)) {
            mainValueText.set("")
            calculating.set(false)
            return false
        }

        if (OperationType.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationType.MULTIPLE.label)
        calculating.set(true)
        return true
    }

    /**
     * ÷をタップされた際の処理
     * mainValueTextが空の場合、return
     * mainValueTextの末尾が.の場合、return
     * mainValueTextが-のみの場合、空に更新してreturn
     * mainValueTextの末尾が四則演算子の場合、÷に入れ替える
     * 上記以外の場合、mainValueTextの末尾に÷を追加
     */
    private fun tapDivide(): Boolean {
        if (mainValueText.get().isNullOrBlank()) { return false }
        if (mainValueText.takeLast(1) == ".") { return false }
        if (mainValueText.get() != null && mainValueText.get().equals(OperationType.MINUS.label)) {
            mainValueText.set("")
            calculating.set(false)
            return false
        }

        if (OperationType.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationType.DIVIDE.label)
        calculating.set(true)
        return true
    }

    fun tapClear() {
        if (calculating.get()) {
            mainValueText.minusLastCharacter()
            supplementaryValueText.set(calculateValue())
        } else {
            mainValueText.set("")
        }
    }

    /**
     * =をタップされた際の処理
     * mainValueTextが空の場合、return
     * calculatingがfalseの場合、return
     */
    fun tapEqual() {
        if (mainValueText.get().isNullOrBlank() || !calculating.get()) { return }

        calculating.set(false)
        mainValueText.set(calculateValue())
        supplementaryValueText.set("")
    }

    private fun calculateValue(): String {
        mainValueText.get()?.let {
            val resultValue = Calculator().call(it)
            return CalculateValueFormatter().call(resultValue)
        }

        // TODO: Exceptionをthrowするかどうかは後で考える
        return "0"
    }
}
