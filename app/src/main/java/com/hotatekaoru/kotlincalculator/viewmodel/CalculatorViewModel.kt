package com.hotatekaoru.kotlincalculator.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.hotatekaoru.kotlincalculator.enum.OperationTypeEnum
import com.hotatekaoru.kotlincalculator.extension.minusLastCharacter
import com.hotatekaoru.kotlincalculator.extension.plus
import com.hotatekaoru.kotlincalculator.extension.takeLast

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
     * +をタップされた際の処理
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
     * mainValueTextの末尾が.の場合、return
     * 上記以外の場合、末尾に.を加える
     */
    fun tapDot() {
        if (mainValueText.takeLast(1) == ".") { return }
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
        if (mainValueText.get() != null && mainValueText.get().equals(OperationTypeEnum.MINUS.string)) {
            mainValueText.set("")
            calculating.set(false)
            return
        }

        if (OperationTypeEnum.values().any { it.string == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.PLUS.string)
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
        if (OperationTypeEnum.values().any { it.string == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.MINUS.string)
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
        if (mainValueText.get() != null && mainValueText.get().equals(OperationTypeEnum.MINUS.string)) {
            mainValueText.set("")
            calculating.set(false)
            return
        }

        if (OperationTypeEnum.values().any { it.string == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.MULTIPLE.string)
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
        if (mainValueText.get() != null && mainValueText.get().equals(OperationTypeEnum.MINUS.string)) {
            mainValueText.set("")
            calculating.set(false)
            return
        }

        if (OperationTypeEnum.values().any { it.string == mainValueText.takeLast(1) }) {
            mainValueText.minusLastCharacter()
        }
        mainValueText.plus(OperationTypeEnum.DIVIDE.string)
        calculating.set(true)
    }

    fun tapClear() {
        if (calculating.get()) {
            mainValueText.minusLastCharacter()
        } else {
            mainValueText.set("")
        }
    }

    fun tapEqual() {
        // TODO: mainValueTextが空の場合、return
        calculating.set(false)
        // TODO: mainValueTextの末尾が.の場合、末尾に0をつけて計算する
        // TODO: mainValueTextの末尾が四則演算子の場合、末尾の四則演算子を削除して計算する
        // TODO: 上記以外の場合、mainValueTextの末尾に÷を追加
        mainValueText.set(calculateValue().toString())
        supplementaryValueText.set("")
    }

    private fun calculateValue(): Double {
        // TODO: 計算を行う
        return 1.23
    }
}
