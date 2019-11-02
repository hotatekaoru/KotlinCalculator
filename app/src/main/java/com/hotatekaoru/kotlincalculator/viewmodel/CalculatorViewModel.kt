package com.hotatekaoru.kotlincalculator.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    /**
     * 入力中はtapされたボタンの内容をそのまま表示するText
     * 計算後（=を押されたあと）は計算結果を表示する
     */
    var mainValueText = ObservableField<String>("")

    fun tapNumber(number: Number) {
        mainValueText.set(mainValueText.get().plus(number.toString()))
    }
}
