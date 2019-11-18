package com.hotatekaoru.kotlincalculator.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.hotatekaoru.kotlincalculator.enum.OperationTypeEnum
import com.hotatekaoru.kotlincalculator.extension.minusLastCharacter
import com.hotatekaoru.kotlincalculator.extension.plus
import com.hotatekaoru.kotlincalculator.extension.takeLast
import com.hotatekaoru.kotlincalculator.model.Calculator

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
    }

    /**
     * 小数点をタップされた際の処理
     * mainValueTextの四則演算子以降に小数点が既にある場合、return
     * 上記以外の場合、末尾に.を加える
     */
    fun tapDot() {
        mainValueText.get()?.let { text ->
            val operationIndex = text.lastIndexOfAny(OperationTypeEnum.values().map { it.label })
            if (text.substring(operationIndex + 1).contains(".")) { return }
        }

        mainValueText.plus(".")
        calculating.set(true)
    }

    /**
     * +をタップされた際の処理
     * mainValueTextが空の場合、return
     * mainValueTextの末尾が.の場合、return
     * mainValueTextが-のみの場合、空に更新してreturn
     * mainValueTextの末尾が四則演算子の場合、+に入れ替える
     * 上記以外の場合、mainValueTextの末尾に+を追加
     */
    fun tapPlus() {
        if (mainValueText.get().isNullOrBlank()) { return }
        if (mainValueText.takeLast(1) == ".") { return }
        if (mainValueText.get() != null && mainValueText.get().equals(OperationTypeEnum.MINUS.label)) {
            mainValueText.set("")
            calculating.set(false)
            return
        }

        if (OperationTypeEnum.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.PLUS.label)
        calculating.set(true)
    }

    /**
     * -をタップされた際の処理
     * mainValueTextの末尾が.の場合、return
     * mainValueTextの末尾が四則演算子の場合、-に入れ替える
     * 上記以外の場合、mainValueTextの末尾に-を追加
     */
    fun tapMinus() {
        if (mainValueText.takeLast(1) == ".") { return }
        if (OperationTypeEnum.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.MINUS.label)
        calculating.set(true)
    }

    /**
     * ×をタップされた際の処理
     * mainValueTextが空の場合、return
     * mainValueTextの末尾が.の場合、return
     * mainValueTextが-のみの場合、空に更新してreturn
     * mainValueTextの末尾が四則演算子の場合、×に入れ替える
     * 上記以外の場合、mainValueTextの末尾に×を追加
     */
    fun tapMultiple() {
        if (mainValueText.get().isNullOrBlank()) { return }
        if (mainValueText.takeLast(1) == ".") { return }
        if (mainValueText.get() != null && mainValueText.get().equals(OperationTypeEnum.MINUS.label)) {
            mainValueText.set("")
            calculating.set(false)
            return
        }

        if (OperationTypeEnum.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.MULTIPLE.label)
        calculating.set(true)
    }

    /**
     * ÷をタップされた際の処理
     * mainValueTextが空の場合、return
     * mainValueTextの末尾が.の場合、return
     * mainValueTextが-のみの場合、空に更新してreturn
     * mainValueTextの末尾が四則演算子の場合、÷に入れ替える
     * 上記以外の場合、mainValueTextの末尾に÷を追加
     */
    fun tapDivide() {
        if (mainValueText.get().isNullOrBlank()) { return }
        if (mainValueText.takeLast(1) == ".") { return }
        if (mainValueText.get() != null && mainValueText.get().equals(OperationTypeEnum.MINUS.label)) {
            mainValueText.set("")
            calculating.set(false)
            return
        }

        if (OperationTypeEnum.values().any { it.label == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.DIVIDE.label)
        calculating.set(true)
    }

    fun tapClear() {
        if (calculating.get()) {
            mainValueText.minusLastCharacter()
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
        mainValueText.set(calculateValue().toString())
        supplementaryValueText.set("")
    }

    private fun calculateValue(): Double {
        mainValueText.get()?.let {
            return Calculator(it).call()
        }

        // TODO: Exceptionをthrowするかどうかは後で考える
        return 0.0
    }
}
