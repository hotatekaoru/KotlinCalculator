package com.hotatekaoru.kotlincalculator.viewmodel

import androidx.databinding.ObservableBoolean
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
    var calculating = ObservableBoolean(false)

    fun tapNumber(number: Number) {
        // TODO: calculatingがtrueの場合、mainValueTextの末尾に入力された数字を加える
        // TODO: calculatingがfalseの場合、mainValueTextを入力された数字に入れ替える
        constructFormula(number.toString())
    }

    fun tapDot() {
        // TODO: mainValueTextの末尾が.の場合、return
        // TODO: 上記以外の場合、末尾に.を加える
        constructFormula(".")
    }

    fun tapPlus() {
        // TODO: mainValueTextが空の場合、return
        // TODO: mainValueTextの末尾が.の場合、return
        // TODO: mainValueTextが-のみの場合、空に更新してreturn
        // TODO: mainValueTextの末尾が四則演算子の場合、+に入れ替える
        // TODO: 上記以外の場合、mainValueTextの末尾に+を追加
        constructFormula(OperationTypeEnum.PLUS.string)
    }

    fun tapMinus() {
        // TODO: mainValueTextの末尾が.の場合、return
        // TODO: mainValueTextの末尾が四則演算子の場合、-に入れ替える
        // TODO: 上記以外の場合、mainValueTextの末尾に-を追加
        constructFormula(OperationTypeEnum.MINUS.string)
    }

    fun tapMultiple() {
        // TODO: mainValueTextが空の場合、return
        // TODO: mainValueTextの末尾が.の場合、return
        // TODO: mainValueTextが-のみの場合、空に更新してreturn
        // TODO: mainValueTextの末尾が四則演算子の場合、×に入れ替える
        // TODO: 上記以外の場合、mainValueTextの末尾に×を追加
        constructFormula(OperationTypeEnum.MULTIPLE.string)
    }

    fun tapDivide() {
        // TODO: mainValueTextが空の場合、return
        // TODO: mainValueTextの末尾が.の場合、return
        // TODO: mainValueTextが-のみの場合、空に更新してreturn
        // TODO: mainValueTextの末尾が四則演算子の場合、÷に入れ替える
        // TODO: 上記以外の場合、mainValueTextの末尾に÷を追加
        constructFormula(OperationTypeEnum.DIVIDE.string)
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

    /**
     * 入力中の数値・計算式を配列に追加する（TODO）
     * 計算式として誤っている場合は、この処理を呼び出さない
     */
    private fun constructFormula(string: String) {
        if (calculating.get()) {
            mainValueText.plus(string)
        } else {
            mainValueText.set(string)
            calculating.set(true)
        }
    }

    private fun calculateValue(): Double {
        // TODO: 計算を行う
        return 1.23
    }
}
